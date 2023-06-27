package de.ariesbuildings.config;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.config.serializers.AriesSerializers;
import io.leangen.geantyref.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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

    @SneakyThrows
    public void removeObject(String path) {
        ConfigurationNode node = config().node(path);
        if (node.virtual()) return;
        config().removeChild(node.key());
        save();
    }

    @SneakyThrows
    public <V> Optional<V> nodeValue(ConfigurationNode rootNode, String nodePath, Class<V> type) {
        return Optional.ofNullable(rootNode.node(nodePath).get(type));
    }

    @SneakyThrows
    public <V> Optional<V> nodeValue(ConfigurationNode rootNode, String nodePath, TypeToken<V> token) {
        return Optional.ofNullable(rootNode.node(nodePath).get(token));
    }

    @SneakyThrows
    public <V> Optional<List<V>> nodeListValue(ConfigurationNode rootNode, String nodePath, Class<V> type) {
        return Optional.ofNullable(rootNode.node(nodePath).getList(type));
    }

    @SneakyThrows
    public void setNodeValue(ConfigurationNode rootNode, String path, Object value) {
        rootNode.node(path).set(value);
    }

    @SneakyThrows
    public <V> void setNodeValue(ConfigurationNode rootNode, String path, V value, TypeToken<V> token) {
        rootNode.node(path).set(token, value);
    }

    abstract void onSerialize(ConfigurationNode objectNode, T object);

    abstract void onDeserialize(ConfigurationNode objectNode, T object);

}
