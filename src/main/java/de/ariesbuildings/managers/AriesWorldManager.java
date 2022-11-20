package de.ariesbuildings.managers;

import com.google.common.collect.Maps;
import de.ariesbuildings.world.AriesWorld;
import org.bukkit.World;

import java.util.HashMap;
import java.util.UUID;

public class AriesWorldManager {

    private final HashMap<UUID, AriesWorld> worldMap = Maps.newHashMap();

    public AriesWorldManager() {
    }

    public AriesWorld getWorld(World world) {
        if (worldMap.containsKey(world.getUID())) return worldMap.get(world.getUID());
        AriesWorld ariesWorld = new AriesWorld(world);
        worldMap.put(world.getUID(), ariesWorld);
        return ariesWorld;
    }

}
