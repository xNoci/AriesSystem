package de.ariesbuildings.managers;

import com.google.common.collect.Lists;
import de.ariesbuildings.config.AriesSystemConfig;
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
        int worldsLoaded = 0;
        AriesSystemConfig.debug("Loading worlds...");

        for (String savedWorld : worldData.getSavedWorlds()) {
            AriesSystemConfig.debug("Loading world '%s'...".formatted(savedWorld));

            AriesWorld ariesWorld = new AriesWorld(savedWorld);
            worldData.deserialize(savedWorld, ariesWorld);
            ariesWorld.load();
            worlds.add(ariesWorld);

            AriesSystemConfig.debug("Loaded world '%s'.".formatted(savedWorld));
            worldsLoaded++;
        }

        AriesSystemConfig.debug("Worlds loaded. Loaded a total of %s worlds.".formatted(worldsLoaded));
    }

    public void saveWorlds() {
        int worldsSaved = 0;
        AriesSystemConfig.debug("Saving worlds...");

        for (AriesWorld world : worlds) {
            AriesSystemConfig.debug("Saving world '%s'...".formatted(world.getWorldName()));
            world.unload();
            worldData.serialize(world.getWorldName(), world);

            AriesSystemConfig.debug("Saved world '%s'.".formatted(world.getWorldName()));
            worldsSaved++;
        }
        worlds.clear();

        AriesSystemConfig.debug("Worlds saved. Saved a total of %s worlds.".formatted(worldsSaved));
    }

    public boolean existsWorld(String worldName) {
        boolean worldExists = getWorld(worldName) != null;
        File worldFile = new File(Bukkit.getWorldContainer(), worldName);
        return worldExists || worldFile.exists();
    }

    public WorldImportResult importWorld(String worldName) {
        File worldFile = new File(Bukkit.getWorldContainer(), worldName);

        if (!worldFile.exists() || !worldFile.isDirectory()) return WorldImportResult.WORLD_NOT_EXIST;
        if (getWorld(worldName) != null) return WorldImportResult.ALREADY_IMPORTED;

        AriesSystemConfig.debug("Importing world %s...".formatted(worldName));


        AriesWorld world = new AriesWorld(worldName);
        world.setType(WorldType.IMPORTED);

        World bukkitWorld = new WorldCreator(worldName).loadWorld();
        world.setWorld(bukkitWorld);

        worlds.add(world);
        worldData.serialize(worldName, world);

        AriesSystemConfig.debug("World %s successfully imported.".formatted(worldName));

        return WorldImportResult.SUCCESS;
    }

    public WorldImportResult unimportWorld(String worldName) {
        AriesWorld world = getWorld(worldName);
        if (world == null) return WorldImportResult.NOT_IMPORTED;

        AriesSystemConfig.debug("Unimporting world %s...".formatted(worldName));

        world.unload();
        worlds.remove(world);
        worldData.removeObject(world.getWorldName());

        AriesSystemConfig.debug("World %s successfully unimported.".formatted(worldName));

        return WorldImportResult.SUCCESS;
    }

    public AriesWorld getWorld(Entity entityInWorld) {
        if (entityInWorld == null) return null;
        return getWorld(entityInWorld.getWorld());
    }

    public AriesWorld getWorld(World world) {
        if (world == null) return null;
        return getWorld(world.getName());
    }

    public AriesWorld getWorld(String worldName) {
        return worlds.stream()
                .filter(world -> world.getWorldName().equalsIgnoreCase(worldName))
                .findFirst()
                .orElse(null);
    }

}
