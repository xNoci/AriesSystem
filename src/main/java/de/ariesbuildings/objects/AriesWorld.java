package de.ariesbuildings.objects;

import com.google.common.collect.Sets;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.events.PostOptionChangeEvent;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class AriesWorld extends OptionHolder<WorldOption> {

    private final World world;
    private final HashSet<UUID> builders = Sets.newHashSet();//TODO Aus Config holen
    private UUID worldCreator;  //TODO Aus Config holen

    public AriesWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public boolean isBuilder(UUID uuid) {
        return builders.contains(uuid) || worldCreator.equals(uuid);
    }

    public boolean hasWorldPermission(Player player) {
        return isBuilder(player.getUniqueId()) || player.hasPermission(Permission.WORLD_BYPASS_BUILDER);
    }

    public void broadcast(String message) {
        if (world == null) return;
        world.getPlayers().forEach(player -> player.sendMessage(message));
    }

    @Override
    protected OptionChangeEvent createOptionChangeEvent(WorldOption option, Object oldValue, Object newValue) {
        return new OptionChangeEvent(option, oldValue, newValue, this);
    }

    @Override
    protected PostOptionChangeEvent createPostOptionChangeEvent(WorldOption option, Object oldValue, Object newValue) {
        return new PostOptionChangeEvent(option, oldValue, newValue, this);
    }
}
