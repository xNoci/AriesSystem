package de.ariesbuildings.config;

import org.spongepowered.configurate.ConfigurationNode;

public abstract class AbstractObjectConfig<T> extends AbstractConfig {

    public AbstractObjectConfig(String path, int configVersion) {
        super(path, configVersion);
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
