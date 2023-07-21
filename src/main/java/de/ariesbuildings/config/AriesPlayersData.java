package de.ariesbuildings.config;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.config.serializers.AriesSerializers;
import de.ariesbuildings.world.RawLocation;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.Optional;
import java.util.UUID;

public class AriesPlayersData extends AbstractObjectData<AriesPlayer> {

    public AriesPlayersData() {
        super("players.data.json", 1);
    }

    public Optional<String> getName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) return Optional.of(player.getName());
        return nodeValue(config().node(uuid.toString()), "name", String.class);
    }

    @SneakyThrows
    public Optional<UUID> getUUID(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) return Optional.of(player.getUniqueId());
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
        setNodeValue(node, "lastKnownLocation", player.getLastKnownLocation());
    }

    @Override
    void onDeserialize(ConfigurationNode node, AriesPlayer player) {
        nodeValue(node, "options", AriesSerializers.Type.PLAYER_OPTION_MAP).ifPresent(options -> player.getOptions().setOptions(options));
        nodeValue(node, "lastKnownLocation", RawLocation.class).ifPresent(player::setLastKnownLocation);
    }

}
