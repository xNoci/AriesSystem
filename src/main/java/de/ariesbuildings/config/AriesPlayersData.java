package de.ariesbuildings.config;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.config.serializers.AriesSerializers;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.Optional;
import java.util.UUID;

public class AriesPlayersData extends AbstractObjectData<AriesPlayer> {

    public AriesPlayersData() {
        super("players.data.json", 1);
    }

    public Optional<String> getName(UUID uuid) {
        return nodeValue(config().node(uuid.toString()), "name", String.class);
    }

    @SneakyThrows
    public Optional<UUID> getUUID(String name) {
        return config().childrenMap().entrySet().stream()
                .filter(entry -> {
                    var nameNode = entry.getValue().childrenMap().get("name");
                    return !nameNode.virtual() && name.equalsIgnoreCase(nameNode.getString());
                })
                .map(entry -> UUID.fromString((String) entry.getKey()))
                .findFirst();
    }

    @Override
    void onSerialize(ConfigurationNode node, AriesPlayer player) {
        setNodeValue(node, "name", player.getName());
        setNodeValue(node, "options", player.getOptions().getOptions(), AriesSerializers.Type.PLAYER_OPTION_MAP);
    }

    @Override
    void onDeserialize(ConfigurationNode node, AriesPlayer player) {
        nodeValue(node, "options", AriesSerializers.Type.PLAYER_OPTION_MAP).ifPresent(options -> player.getOptions().setOptions(options));
    }

}
