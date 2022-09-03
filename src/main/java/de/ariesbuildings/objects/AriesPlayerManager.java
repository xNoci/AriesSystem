package de.ariesbuildings.objects;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AriesPlayerManager {

    private static HashMap<UUID, AriesPlayer> PLAYER_MAP = Maps.newHashMap();


    public static AriesPlayer getPlayer(Player player) {
        if (PLAYER_MAP.containsKey(player.getUniqueId())) return PLAYER_MAP.get(player.getUniqueId());
        AriesPlayer ariesPlayer = new AriesPlayer(player);
        PLAYER_MAP.put(player.getUniqueId(), ariesPlayer);
        return ariesPlayer;
    }

}
