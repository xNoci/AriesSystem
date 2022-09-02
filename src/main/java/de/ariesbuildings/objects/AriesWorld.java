package de.ariesbuildings.objects;

import de.ariesbuildings.options.Option;
import org.bukkit.World;

import java.util.HashMap;
import java.util.HashSet;

public class AriesWorld {
    private final World world;
    private AriesPlayer owner;
    private final HashMap<Option, String> options;
    private final HashSet<AriesPlayer> trusted;

    public AriesWorld(World world) {
        this.world = world;
        this.owner = null; //TODO Aus Config holen
        this.options = new HashMap<>(); //TODO Aus Config holen
        this.trusted = new HashSet<>(); //TODO Aus Config holen
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

    public AriesPlayer getOwner() {
        return owner;
    }

    public HashSet<AriesPlayer> getTrusted() {
        return trusted;
    }


    public void setOwner(AriesPlayer owner) {
        this.owner = owner;
    }
}
