package de.ariesbuildings.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.config.AriesWorldsData;
import de.ariesbuildings.utils.Input;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldImportResult;
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.WorldVisibility;
import de.ariesbuildings.world.creator.CreatorID;
import de.ariesbuildings.world.creator.WorldCreator;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AriesWorldManager {

    private final AriesWorldsData worldData = new AriesWorldsData();
    private final List<AriesWorld> worlds = Lists.newArrayList();
    private final Cache<String, AriesWorld> worldCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(50)
            .build();

    private final JavaPlugin plugin;

    public AriesWorldManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadSavedWorlds() {
        int worldsLoaded = 0;
        AriesSystemConfig.debug("Loading worlds...");

        for (String savedWorld : worldData.getSavedWorlds()) {
            AriesWorld ariesWorld = new AriesWorld(savedWorld);
            worldData.deserialize(savedWorld, ariesWorld);
            ariesWorld.load();
            worlds.add(ariesWorld);
            AriesSystemConfig.debug("Loaded world '%s'.", savedWorld);
            worldsLoaded++;
        }

        AriesSystemConfig.debug("Worlds loaded. Loaded a total of %s worlds.", worldsLoaded);
    }

    public void saveWorlds() {
        int worldsSaved = 0;
        AriesSystemConfig.debug("Saving worlds...");

        for (AriesWorld world : worlds) {
            AriesSystemConfig.debug("Saving world '%s'...", world.getWorldName());
            worldData.serialize(world.getWorldName(), world);

            worldsSaved++;
        }
        worlds.clear();

        AriesSystemConfig.debug("Worlds saved. Saved a total of %s worlds.", worldsSaved);
    }

    public boolean existsWorld(String worldName) {
        boolean worldExists = getWorld(worldName).isPresent();
        File worldFile = new File(Bukkit.getWorldContainer(), worldName);
        return worldExists || worldFile.exists();
    }

    public void createWorld(AriesPlayer player, WorldType worldType) {
        Input.title(player, I18n.translate("input.title.world_create"), input -> {
            if (StringUtils.isBlank(input)) {
                player.sendTranslate("input.world_create.empty_string");
                return;
            }

            if (!createWorld(input, player.getUUID(), worldType)) {
                player.sendTranslate("world.creation.failed");
                return;
            }
            player.sendTranslate("world.creation.success");
        }, p -> p.sendTranslate("input.world_create.canceled"));
    }

    public boolean createWorld(String worldName, UUID creator, WorldType type) {
        if (existsWorld(worldName)) return false; //WORLD ALREADY EXIST
        if (type == WorldType.IMPORTED) {
            type = WorldType.NORMAL;
        }

        AriesWorld world = new AriesWorld(worldName);
        if (creator != null) {
            world.setWorldCreator(creator);
        }
        world.setType(type);
        Bukkit.getScheduler().runTask(plugin, () -> {
            World bukkitWorld = new WorldCreator(world).createWorld();
            world.setWorld(bukkitWorld);
        });
        world.getType().getCreator().applyDefaultSettings(world);

        worldData.serialize(worldName, world);
        worlds.add(world);
        return true;
    }

    public WorldImportResult importWorld(String worldName) {
        File worldFile = new File(Bukkit.getWorldContainer(), worldName);

        if (!worldFile.exists() || !worldFile.isDirectory()) return WorldImportResult.WORLD_NOT_EXIST;
        if (getWorld(worldName).isPresent()) return WorldImportResult.ALREADY_IMPORTED;

        AriesSystemConfig.debug("Importing world %s...", worldName);

        AriesWorld world = new AriesWorld(worldName);
        world.setType(WorldType.IMPORTED);
        world.setVisibility(WorldVisibility.PUBLIC);
        world.setWorldCreator(CreatorID.IMPORTED.getUUID());

        World bukkitWorld = new WorldCreator(worldName).loadWorld();
        world.setWorld(bukkitWorld);

        worlds.add(world);
        worldData.serialize(worldName, world);

        AriesSystemConfig.debug("World %s successfully imported.", worldName);

        return WorldImportResult.SUCCESS;
    }

    public WorldImportResult unimportWorld(String worldName) {
        Optional<AriesWorld> worldOptional = getWorld(worldName);
        if (worldOptional.isEmpty()) return WorldImportResult.NOT_IMPORTED;

        AriesSystemConfig.debug("Unimporting world %s...", worldName);

        AriesWorld world = worldOptional.get();
        world.unload();
        worlds.remove(world);
        worldData.removeObject(world.getWorldName());

        AriesSystemConfig.debug("World %s successfully unimported.", worldName);

        return WorldImportResult.SUCCESS;
    }

    public Optional<AriesWorld> getWorld(AriesPlayer player) {
        if (!player.isValid()) return Optional.empty();
        return getWorld(player.getBase());
    }

    public Optional<AriesWorld> getWorld(Entity entityInWorld) {
        if (entityInWorld == null) return Optional.empty();
        return getWorld(entityInWorld.getWorld());
    }

    public Optional<AriesWorld> getWorld(World world) {
        if (world == null) return Optional.empty();
        return getWorld(world.getName());
    }

    @SneakyThrows
    public Optional<AriesWorld> getWorld(String worldName) {
        AriesWorld cachedWorld = worldCache.getIfPresent(worldName.toLowerCase());
        if (cachedWorld != null) return Optional.of(cachedWorld);

        Optional<AriesWorld> optionalWorld = worlds.stream()
                .filter(world -> world.getWorldName().equalsIgnoreCase(worldName))
                .findFirst();

        optionalWorld.ifPresent(world -> worldCache.put(worldName.toLowerCase(), world));
        return optionalWorld;
    }

    public List<AriesWorld> getWorlds() {
        return ImmutableList.copyOf(worlds);
    }

    public List<AriesWorld> getWorlds(WorldVisibility visibility) {
        List<AriesWorld> copiedWorlds = Lists.newArrayList(worlds);
        return copiedWorlds.stream()
                .filter(world -> world.getVisibility() == visibility)
                .collect(ImmutableList.toImmutableList());

    }

    public List<AriesWorld> getWorlds(WorldVisibility visibility, AriesPlayer player) {
        return getWorlds(visibility).stream()
                .filter(world -> {
                    if (visibility != WorldVisibility.PRIVATE) return true;
                    return world.isPermitted(player);
                })
                .collect(ImmutableList.toImmutableList());

    }
}
