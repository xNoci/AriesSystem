package de.ariesbuildings.objects;

import de.ariesbuildings.options.PlayerOption;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AriesPlayer {
    private final Player player;
    private final HashMap<PlayerOption, String> options;

    public AriesPlayer(Player player) {
        this.player = player;
        this.options = new HashMap<>();
    }

    public static AriesPlayer getAriesPlayer(Player player) {
        return new AriesPlayer(player);
    }

    public Player getPlayer() {
        return player;
    }

    public HashMap<PlayerOption, String> getOptions() {
        return options;
    }
}
