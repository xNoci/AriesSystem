package de.ariesbuildings.config;

import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.serializers.AriesSerializers;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;

public class AriesPlayersConfig extends AbstractObjectConfig<AriesPlayer> {

    public AriesPlayersConfig() {
        super("player_settings.json");
    }

    @Override
    @SneakyThrows
    void onSerialize(ConfigurationNode objectNode, AriesPlayer player) {
        objectNode.node("name").set(player.getName());
        objectNode.node("options").set(player.getOptions());
    }

    @Override @SneakyThrows
    void onDeserialize(ConfigurationNode objectNode, AriesPlayer player) {
        OptionMap<PlayerOption> options = objectNode.node("options").get(AriesSerializers.PLAYER_OPTION_TYPE);
        if(options != null) {
            for (PlayerOption key : options.getKeys()) {
                player.setOption(key, options.get(key));
            }
        }
    }

}
