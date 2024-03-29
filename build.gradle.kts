import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.ariesbuildings"
version = "b${gitRevision()}.${gitHash()}"

repositories {
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") } //PaperMC
}

dependencies {
    implementation("io.papermc:paperlib:1.0.7")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.spongepowered:configurate-gson:4.1.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("com.github.cryptomorin:XSeries:9.5.0")

    compileOnly(files("libs/QuickUtils.jar"))
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

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
    minimize()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    shadowJar {
        archiveFileName.set("${project.property("plugin.name")}-${project.version}.jar")
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
                "plugin.version" to version,
                "plugin.main" to project.property("plugin.main"),
                "plugin.authors" to project.property("plugin.authors")
            )
        )
    }
}

fun gitRevision() : String {
    val out = ByteArrayOutputStream();
    exec {
        commandLine("git", "rev-list", "--count", "HEAD")
        standardOutput = out;
    }
    return out.toString("UTF-8").trim();
}

fun gitHash() : String {
    val out = ByteArrayOutputStream();
    exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = out;
    }
    return out.toString("UTF-8").trim();
}