package de.ariesbuildings.world.creator.types;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public interface WorldCreatorType {

    default World generateBukkitWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        applyWorldCreator(worldCreator);

        World world = Bukkit.createWorld(worldCreator);
        applyWorldGeneration(world);

        return world;
    }

    default World loadBukkitWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        applyWorldCreator(worldCreator);
        return Bukkit.createWorld(worldCreator);
    }

    void applyWorldCreator(WorldCreator creator);

    void applyWorldGeneration(World world);

}
