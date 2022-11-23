package de.ariesbuildings.config;

import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.config.serializers.AriesSerializers;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;

public class AriesPlayersData extends AbstractObjectData<AriesPlayer> {

    public AriesPlayersData() {
        super("players.data.json", 1);
    }

    @Override
    @SneakyThrows
    void onSerialize(ConfigurationNode objectNode, AriesPlayer player) {
        objectNode.node("name").set(player.getName());
        objectNode.node("options").set(AriesSerializers.Type.PLAYER_OPTION_MAP, player.getOptions().getOptions());
    }

    @Override
    @SneakyThrows
    void onDeserialize(ConfigurationNode objectNode, AriesPlayer player) {
        OptionMap<PlayerOption> options = objectNode.node("options").get(AriesSerializers.Type.PLAYER_OPTION_MAP);
        if (options != null) {
            player.getOptions().setOptions(options);
        }
    }

}
