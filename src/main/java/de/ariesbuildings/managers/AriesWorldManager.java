package de.ariesbuildings.managers;

import com.google.common.collect.Lists;
import de.ariesbuildings.config.AriesWorldsData;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldImportResult;
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.creator.WorldCreator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class AriesWorldManager {

    private final AriesWorldsData worldData = new AriesWorldsData();
    private final List<AriesWorld> worlds = Lists.newArrayList();

    public AriesWorldManager() {
    }

    public void loadSavedWorlds() {
        for (String savedWorld : worldData.getSavedWorlds()) {
            AriesWorld ariesWorld = new AriesWorld(savedWorld);
            worldData.deserialize(savedWorld, ariesWorld);
            ariesWorld.load();
            worlds.add(ariesWorld);
        }
    }

    public void saveWorlds() {
        for (AriesWorld world : worlds) {
            world.unload();
            worldData.serialize(world.getWorldName(), world);
        }
        worlds.clear();
    }

    public boolean existsWorld(String worldName) {
        boolean worldExists = getWorld(worldName) != null;
        File worldFile = new File(Bukkit.getWorldContainer(), worldName);
        return worldExists || worldFile.exists();
    }
    public AriesWorld getWorld(World world) {
        if (worldMap.containsKey(world.getUID())) return worldMap.get(world.getUID());
        AriesWorld ariesWorld = new AriesWorld(world);
        worldMap.put(world.getUID(), ariesWorld);
        return ariesWorld;
    }

}
