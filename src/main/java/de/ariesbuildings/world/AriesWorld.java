package de.ariesbuildings.world;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.creator.WorldCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class AriesWorld {

    @Getter private final OptionHolder<WorldOption> options;

    @Getter private final String worldName;
    @Getter @Setter private UUID worldCreator;
    @Getter @Setter private long creationTime;
    @Getter @Setter private XMaterial displayIcon;
    @Getter @Setter private WorldType type;
    @Getter @Setter private ArrayList<UUID> builders;


    @Getter @Setter private World world = null;

    public AriesWorld(String worldName) {
        this.worldName = worldName;

        this.options = new OptionHolder<>(this);
        this.worldCreator = null;
        this.creationTime = System.currentTimeMillis();
        this.displayIcon = XMaterial.GRASS_BLOCK;
        this.type = WorldType.VOID;
        this.builders = Lists.newArrayList();

        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            this.world = world;
        }
    }

    public void load() {
        if (isLoaded()) return;

        world = new WorldCreator(this).loadWorld();
    }

    public void unload() {
        if (!isLoaded()) return;

        for (Chunk chunk : world.getLoadedChunks()) {
            chunk.unload(true);
        }

        Bukkit.unloadWorld(world, true);
        world = null;
    }

    public boolean isLoaded() {
        return world != null;
    }

    public boolean isBuilder(UUID uuid) {
        return builders.contains(uuid) || uuid.equals(worldCreator);
    }

    public boolean hasWorldPermission(Player player) {
        return isBuilder(player.getUniqueId()) || player.hasPermission(Permission.WORLD_BYPASS_BUILDER);
    }

    public void broadcast(String message) {
        if (world == null) return;
        world.getPlayers().forEach(player -> player.sendMessage(message));
    }

    public void addBuilder(UUID uuid) {
        this.builders.add(uuid);
    }

}
