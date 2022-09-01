package de.ariesbuildings.objects;

import de.ariesbuildings.options.Option;
import org.bukkit.World;

import java.util.HashMap;

public class AriesWorld {
    private final World world;
    private final HashMap<Option, String> options;

    public AriesWorld(World world) {
        this.world = world;
        this.options = new HashMap<>();
    }

    public static AriesWorld getAriesWorld(World world) {
        return new AriesWorld(world);
    }

    public World getWorld() {
        return world;
    }

    public HashMap<Option, String> getOptions() {
        return options;
    }
}
