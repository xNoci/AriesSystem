package de.ariesbuildings.world;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.creator.CreatorID;
import de.ariesbuildings.world.creator.WorldCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AriesWorld {

    @Getter private final OptionHolder<WorldOption> options;

    @Getter private final String worldName;
    @Getter @Setter private UUID worldCreator;
    @Getter @Setter private long creationTime;
    @Getter @Setter private XMaterial displayIcon;
    @Getter @Setter private RawLocation worldSpawn = new RawLocation(0, 100, 0);
    @Getter @Setter private WorldType type;
    @Getter @Setter private List<UUID> builders;
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
        Bukkit.getWorlds().remove(world);
        world = null;
    }

    public boolean isLoaded() {
        return world != null;
    }

    public boolean isBuilder(UUID uuid) {
        return builders.contains(uuid) || uuid.equals(worldCreator);
    }

    public boolean isCreator(UUID uuid) {
        return uuid.equals(worldCreator);
    }
    public boolean canJoin(AriesPlayer player) {
        return isBuilder(player.getUUID()) || player.hasPermission(Permission.WORLD_BYPASS_BUILDER);
    }

    public boolean hasWorldPermission(Player player, String permission) {
        if (player.hasPermission(Permission.WORLD_BYPASS_BUILDER)) return true;
        return isBuilder(player.getUniqueId()) && player.hasPermission(permission);
    }

    public boolean isPermitted(AriesPlayer player) {
        return isPermitted(player.getBase());
    }

    public boolean isPermitted(Player player) {
        return isBuilder(player.getUniqueId()) || player.hasPermission(Permission.WORLD_BYPASS_BUILDER);
    }

    public void broadcast(String message) {
        if (world == null) return;
        world.getPlayers().forEach(player -> player.sendMessage(message));
    }

    public void broadcast(String message, Predicate<AriesPlayer> filter) {
        if (world == null) return;
        world.getPlayers().stream()
                .map(AriesSystem.getInstance().getPlayerManager()::getPlayer)
                .filter(filter)
                .forEach(player -> player.sendMessage(message));
    }

    public void addBuilder(UUID uuid) {
        this.builders.add(uuid);
    }

    public boolean teleport(Player player, boolean force) {
        return teleport(AriesSystem.getInstance().getPlayerManager().getPlayer(player), force);
    }

    public boolean teleport(AriesPlayer player, boolean force) {
        return teleport(player, force, false);
    }

    public boolean teleport(AriesPlayer player, boolean force, boolean ignoreJoinRestriction) {
        if (!ignoreJoinRestriction && !canJoin(player)) {
            player.sendMessage(I18n.translate("world.join.not_allowed"));
            return false;
        }

        if (!isLoaded()) {
            if (!force) return false;
            load();
        }

        Location location = new Location(world, worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), worldSpawn.getYaw(), worldSpawn.getPitch());
        player.getBase().teleport(location);
        return true;
    }

    public String getCreatorAsString() {
        if (worldCreator == null) {
            return "Unknown";
        }

        Optional<CreatorID> creatorID = CreatorID.match(worldCreator);
        if (creatorID.isPresent()) return creatorID.get().getCreatorName();
        return AriesSystem.getInstance().getPlayerManager().getPlayerName(worldCreator);
    }

    public String getBuildersAsString() {
        if (builders.isEmpty()) return "/";
        return builders.stream()
                .map(uuid -> AriesSystem.getInstance().getPlayerManager().getPlayerName(uuid))
                .collect(Collectors.joining(", "));
    }

    public WorldVisibility getVisibility() {
        return options.get(WorldOption.WORLD_VISIBILITY, WorldVisibility.class);
    }

    public void setVisibility(WorldVisibility visibility) {
        options.set(WorldOption.WORLD_VISIBILITY, visibility);
    }

}
