package de.ariesbuildings.objects;

import de.ariesbuildings.options.Option;
import org.bukkit.World;

import java.util.HashSet;
import java.util.UUID;

public class AriesWorld {
    private final World world;
    private UUID worldCreator;  //TODO Aus Config holen
    private final HashSet<UUID> builders = Sets.newHashSet();//TODO Aus Config holen

    protected AriesWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public boolean isBuilder(UUID uuid) {
        return builders.contains(uuid);
    }

    }
}
