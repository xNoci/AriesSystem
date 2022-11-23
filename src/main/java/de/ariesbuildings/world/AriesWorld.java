package de.ariesbuildings.world;

import com.google.common.collect.Lists;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.creator.WorldCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

public class AriesWorld {

    @Getter private boolean loaded = false;
    @Getter @Nullable private World world = null;

    @Getter private final String worldName;
    @Getter @Setter private WorldType type = WorldType.VOID;

    @Getter @Setter private @Nullable UUID worldCreator = null;
    @Getter @Setter private ArrayList<UUID> builders = Lists.newArrayList();
    @Getter @Setter private long creationTime = System.currentTimeMillis();
    @Getter private final OptionHolder<WorldOption> options = new OptionHolder<>(this);

    public AriesWorld(String worldName) {
        this.worldName = worldName;
    }

    public void load() {

    }

    public void unload() {
        if (!loaded) return;
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

}
