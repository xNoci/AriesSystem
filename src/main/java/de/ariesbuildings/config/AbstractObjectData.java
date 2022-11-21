package de.ariesbuildings.config;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.serializers.AriesSerializers;
import lombok.Getter;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.nio.file.Path;

public abstract class AbstractObjectData<T> {

    @Getter private final int configVersion;

    private final GsonConfigurationLoader configLoader;
    private final ConfigurationNode config;

    @SneakyThrows
    public AbstractObjectData(String path, int configVersion) {
        this.configVersion = configVersion;
        this.configLoader = GsonConfigurationLoader.builder()
                .path(Path.of(AriesSystem.getInstance().getDataFolder().getPath(), path))
                .defaultOptions(options -> options.serializers(builder -> builder.registerAll(AriesSerializers.SERIALIZERS)))
                .build();
        this.config = this.configLoader.load();

        handleVersion();
    }

    @SneakyThrows
    protected void save() {
        this.configLoader.save(this.config);
    }

    protected ConfigurationNode config() {
        return this.config.node("configData");
    }

    @SneakyThrows
    private void handleVersion() {
        ConfigurationNode versionNode = this.config.node("configVersion");
        if (versionNode.virtual()) { //VERSION WAS NOT SET
            versionNode.set(configVersion);
            save();
            return;
        }

        int oldVersion = versionNode.getInt();
        if (oldVersion == configVersion) {
            //CONFIG VERSION DID NOT CHANGE
            return;
        }

        //NEW VERSION
        this.config.node("configVersion").set(configVersion);
        save();
    }

    public void serialize(String path, T object) {
        ConfigurationNode node = config().node(path);

        onSerialize(node, object);

        save();
    }

    public T deserialize(String path, T object) {
        ConfigurationNode node = config().node(path);
        if (node.virtual()) return object;
        onDeserialize(node, object);
        return object;
    }

    abstract void onSerialize(ConfigurationNode objectNode, T object);

    abstract void onDeserialize(ConfigurationNode objectNode, T object);

}
