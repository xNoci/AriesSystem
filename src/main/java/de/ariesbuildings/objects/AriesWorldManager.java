package de.ariesbuildings.objects;

import com.google.common.collect.Maps;
import org.bukkit.World;

import java.util.HashMap;
import java.util.UUID;

public class AriesWorldManager {

    private static HashMap<UUID, AriesWorld> WORLD_MAP = Maps.newHashMap();


    public static AriesWorld getWorld(World world) {
        if (WORLD_MAP.containsKey(world.getUID())) return WORLD_MAP.get(world.getUID());
        AriesWorld ariesWorld = new AriesWorld(world);
        WORLD_MAP.put(world.getUID(), ariesWorld);
        return ariesWorld;
    }

}
