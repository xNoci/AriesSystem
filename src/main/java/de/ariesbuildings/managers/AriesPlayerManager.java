package de.ariesbuildings.managers;

import com.google.common.collect.Maps;
import de.ariesbuildings.objects.AriesPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class AriesPlayerManager {

    private final HashMap<UUID, AriesPlayer> playerMap = Maps.newHashMap();

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
        playerMap.remove(uuid);
    }

    public Collection<AriesPlayer> getPlayers() {
        return new ArrayList<>(playerMap.values());
    }

    private AriesPlayer getOrCreatePlayer(Player player) {
        if (player == null) return null;
        if (playerMap.containsKey(player.getUniqueId())) return playerMap.get(player.getUniqueId());
        AriesPlayer ariesPlayer = new AriesPlayer(player);
        playerMap.put(player.getUniqueId(), ariesPlayer);
        return ariesPlayer;
    }

}
