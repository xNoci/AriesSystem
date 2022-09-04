package de.ariesbuildings.objects;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class AriesPlayerManager {

    private HashMap<UUID, AriesPlayer> PLAYER_MAP = Maps.newHashMap();

    public AriesPlayerManager() {
        Bukkit.getOnlinePlayers().forEach(this::getOrCreatePlayer);
    }

    public AriesPlayer getPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return getPlayer(player);
    }

    public AriesPlayer getPlayer(Player player) {
        return getOrCreatePlayer(player);
    }

    public void removeUser(UUID uuid) {
        PLAYER_MAP.remove(uuid);
    }

    public Collection<AriesPlayer> getPlayers() {
        return new ArrayList<>(PLAYER_MAP.values());
    }

    private AriesPlayer getOrCreatePlayer(Player player) {
        if (player == null) return null;
        if (PLAYER_MAP.containsKey(player.getUniqueId())) return PLAYER_MAP.get(player.getUniqueId());
        AriesPlayer ariesPlayer = new AriesPlayer(player);
        PLAYER_MAP.put(player.getUniqueId(), ariesPlayer);
        return ariesPlayer;
    }

}
