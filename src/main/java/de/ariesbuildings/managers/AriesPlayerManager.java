package de.ariesbuildings.managers;

import com.google.common.collect.Maps;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.config.AriesPlayersData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class AriesPlayerManager {

    private final HashMap<UUID, AriesPlayer> playerMap = Maps.newHashMap();
    private final AriesPlayersData playerData = new AriesPlayersData();

    public AriesPlayerManager() {
        Bukkit.getOnlinePlayers().forEach(this::createPlayer);
    }

    public AriesPlayer getPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return getPlayer(player);
    }

    public AriesPlayer getPlayer(Player player) {
        return createPlayer(player);
    }

    public void removePlayer(UUID uuid) {
        if (!playerMap.containsKey(uuid)) return;
        AriesPlayer player = playerMap.get(uuid);

        playerData.serialize(player.getUUID().toString(), player);
        playerMap.remove(uuid);
    }

    public Collection<AriesPlayer> getPlayers() {
        return new ArrayList<>(playerMap.values());
    }

    private AriesPlayer createPlayer(Player player) {
        if (player == null) return null;
        if (playerMap.containsKey(player.getUniqueId())) return playerMap.get(player.getUniqueId());

        AriesPlayer ariesPlayer = new AriesPlayer(player);
        playerData.deserialize(player.getUniqueId().toString(), ariesPlayer);

        playerMap.put(player.getUniqueId(), ariesPlayer);
        return ariesPlayer;

    }

}
