package de.ariesbuildings.objects;

import de.ariesbuildings.options.PlayerOption;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class AriesPlayer {
    private final Player player;
    private final HashMap<PlayerOption, String> options;
    private final HashSet<AriesWorld> ownedWorlds;
    private final HashSet<AriesWorld> trustedMember;

    public AriesPlayer(Player player) {
        this.player = player;
        this.options = new HashMap<>(); //TODO Aus Config holen
        this.ownedWorlds = new HashSet<>(); //TODO Aus Config holen
        this.trustedMember = new HashSet<>(); //TODO Aus Config holen
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

    public HashSet<AriesWorld> getOwnedWorlds() {
        return ownedWorlds;
    }

    public HashSet<AriesWorld> getTrustedMember() {
        return trustedMember;
    }
}
