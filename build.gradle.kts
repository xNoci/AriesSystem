import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.ariesbuildings"
version = project.property("version")!!

repositories {
    mavenLocal()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") } //PaperMC
}

dependencies {
    implementation("io.papermc:paperlib:1.0.7")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.spongepowered:configurate-gson:4.1.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("com.github.cryptomorin:XSeries:9.1.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    compileOnly(files("libs/QuickUtils.jar"))
    compileOnly("org.spigotmc:minecraft-server:1.19.2-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")

    compileOnly("net.luckperms:api:5.4")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

task<ConfigureShadowRelocation>("relocateShadowJar") {
    target = tasks["shadowJar"] as ShadowJar
    prefix = "de.ariesbuildings.dep"
}

tasks.named<ShadowJar>("shadowJar").configure {
    dependsOn(tasks["relocateShadowJar"])
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    shadowJar {
        archiveFileName.set("${project.property("plugin.name")}-${project.property("plugin.version")}.jar")
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filter<ReplaceTokens>(
            "beginToken" to "\${",
            "endToken" to "}",
            "tokens" to mapOf(
                "plugin.name" to project.property("plugin.name"),
                "plugin.version" to project.property("plugin.version"),
                "plugin.main" to project.property("plugin.main"),
                "plugin.authors" to project.property("plugin.authors")
            )
        )
    }
}

