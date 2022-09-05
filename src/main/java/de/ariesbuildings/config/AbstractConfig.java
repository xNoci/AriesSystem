package de.ariesbuildings.config;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.serializers.AriesSerializers;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.nio.file.Path;

public abstract class AbstractConfig {

    private final GsonConfigurationLoader configLoader;
    private final ConfigurationNode config;

    @SneakyThrows
    public AbstractConfig(String path) {
        this.configLoader = GsonConfigurationLoader.builder()
                .path(Path.of(AriesSystem.getInstance().getDataFolder().getPath(), path))
                .defaultOptions(options -> options.serializers(builder -> builder.registerAll(AriesSerializers.SERIALIZERS)))
                .build();

        this.config = this.configLoader.load();
    }

    @SneakyThrows
    protected void save() {
        this.configLoader.save(this.config);
    }

    protected ConfigurationNode config() {
        return this.config;
    }

}
