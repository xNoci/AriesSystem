package de.ariesbuildings.config;

import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.serializers.AriesSerializers;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.ConfigurationNode;

public class PlayerSettingsConfig extends AbstractConfig {

    private static final String PATH_NAME = "name";
    private static final String PATH_OPTIONS = "options";

    public PlayerSettingsConfig() {
        super("player_settings.json");
    }

    @SneakyThrows
    public void savePlayer(AriesPlayer player) {
        ConfigurationNode playerDataNode = config().node(player.getUUID().toString());

        playerDataNode.node(PATH_NAME).set(player.getName());
        playerDataNode.node(PATH_OPTIONS).set(player.getOptions());

        save();
    }

    public AriesPlayer loadPlayer(Player player) {
        ConfigurationNode playerDataNode = config().node(player.getUniqueId().toString());
        if (playerDataNode.virtual()) return new AriesPlayer(player);
        AriesPlayer ariesPlayer = new AriesPlayer(player);

        loadPlayerOptions(ariesPlayer, playerDataNode);

        return ariesPlayer;
    }

    @SneakyThrows
    private void loadPlayerOptions(AriesPlayer player, ConfigurationNode playerDataNode) {
        ConfigurationNode playerOption = playerDataNode.node(PATH_OPTIONS);
        OptionMap<PlayerOption> options = playerOption.get(AriesSerializers.PLAYER_OPTION_TYPE);
        if (options != null) {
            for (PlayerOption key : options.getKeys()) {
                player.setOption(key, options.get(key));
            }
        }
    }

}
