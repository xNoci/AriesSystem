package de.ariesbuildings.config;

import de.ariesbuildings.ServerData;
import org.spongepowered.configurate.ConfigurationNode;

public class ServerDataSerializer extends AbstractObjectData<ServerData> {

    public ServerDataSerializer() {
        super("server.data.json", 1);
    }


    @Override
    public void serialize(String path, ServerData object) {
        ConfigurationNode node = config();

        onSerialize(node, object);

        save();
    }

    public ServerData deserialize(String path, ServerData object) {
        ConfigurationNode node = config();
        if (node.virtual()) return object;
        onDeserialize(node, object);
        return object;
    }


    @Override
    void onSerialize(ConfigurationNode rootNode, ServerData setting) {
        setNodeValue(rootNode, "tutorialWorldLoaded", setting.isTutorialWorldLoaded());
        setNodeValue(rootNode, "spawnWorld", setting.getSpawnWorldName());
    }

    @Override
    void onDeserialize(ConfigurationNode rootNode, ServerData setting) {
        nodeValue(rootNode, "tutorialWorldLoaded", Boolean.class).ifPresent(setting::setTutorialWorldLoaded);
        nodeValue(rootNode, "spawnWorld", String.class).ifPresent(setting::setSpawnWorld);
    }

}
