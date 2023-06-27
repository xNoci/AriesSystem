package de.ariesbuildings.config;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.config.serializers.AriesSerializers;
import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;

public class AriesPlayersData extends AbstractObjectData<AriesPlayer> {

    public AriesPlayersData() {
        super("players.data.json", 1);
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
