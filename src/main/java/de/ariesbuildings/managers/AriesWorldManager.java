package de.ariesbuildings.managers;

import com.google.common.collect.Lists;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.config.AriesWorldsData;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldImportResult;
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.WorldVisibility;
import de.ariesbuildings.world.creator.WorldCreator;
import me.noci.quickutilities.input.TitledPlayerChatInput;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AriesWorldManager {

    private final AriesWorldsData worldData = new AriesWorldsData();
    private final List<AriesWorld> worlds = Lists.newArrayList();

    private final JavaPlugin plugin;

    public AriesWorldManager(JavaPlugin plugin) {
        this.plugin = plugin;
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
            worldData.serialize(world.getWorldName(), world);

            worldsSaved++;
        }
        worlds.clear();

        AriesSystemConfig.debug("Worlds saved. Saved a total of %s worlds.".formatted(worldsSaved));
    }

    public boolean existsWorld(String worldName) {
        boolean worldExists = getWorld(worldName).isPresent();
        File worldFile = new File(Bukkit.getWorldContainer(), worldName);
        return worldExists || worldFile.exists();
    }

    public void createWorld(Player player, WorldType worldType) {
        var playerInput = new TitledPlayerChatInput(plugin, player, AriesSystemConfig.PLAYER_INPUT_CANCEL, input -> {
            if (StringUtils.isBlank(input)) {
                player.sendMessage(I18n.translate("input.world_create.empty_string"));
                return;
            }

            if (!createWorld(input, player.getUniqueId(), worldType)) {
                player.sendMessage(I18n.translate("world_creation.failed"));
                return;
            }
            player.sendMessage(I18n.translate("world_creation.success"));

        }, I18n.translate("input.world_create.title"), I18n.translate("input.world_create.subtitle", AriesSystemConfig.PLAYER_INPUT_CANCEL));

        playerInput.onCancel(p -> {
            p.sendMessage(I18n.translate("input.world_create.canceled"));
        });
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
            World bukkitWorld = new WorldCreator(world).generateWorld();
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

        AriesSystemConfig.debug("Importing world %s...".formatted(worldName));

        AriesWorld world = new AriesWorld(worldName);
        world.setType(WorldType.IMPORTED);
        world.setVisibility(WorldVisibility.PUBLIC);

        World bukkitWorld = new WorldCreator(worldName).loadWorld();
        world.setWorld(bukkitWorld);

        worlds.add(world);
        worldData.serialize(worldName, world);

        AriesSystemConfig.debug("World %s successfully imported.".formatted(worldName));

        return WorldImportResult.SUCCESS;
    }

    public WorldImportResult unimportWorld(String worldName) {
        Optional<AriesWorld> worldOptional = getWorld(worldName);
        if (worldOptional.isEmpty()) return WorldImportResult.NOT_IMPORTED;

        AriesSystemConfig.debug("Unimporting world %s...".formatted(worldName));

        AriesWorld world = worldOptional.get();
        world.unload();
        worlds.remove(world);
        worldData.removeObject(world.getWorldName());

        AriesSystemConfig.debug("World %s successfully unimported.".formatted(worldName));

        return WorldImportResult.SUCCESS;
    }

    public Optional<AriesWorld> getWorld(Entity entityInWorld) {
        if (entityInWorld == null) return Optional.empty();
        return getWorld(entityInWorld.getWorld());
    }

    public Optional<AriesWorld> getWorld(World world) {
        if (world == null) return Optional.empty();
        return getWorld(world.getName());
    }

    public Optional<AriesWorld> getWorld(String worldName) {
        //TODO CACHE THE WORLDS
        return worlds.stream()
                .filter(world -> world.getWorldName().equalsIgnoreCase(worldName))
                .findFirst();
    }

}
