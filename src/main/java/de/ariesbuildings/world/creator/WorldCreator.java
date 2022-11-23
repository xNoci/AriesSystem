package de.ariesbuildings.world.creator;

import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldType;
import org.bukkit.World;

public class WorldCreator {

    private final String worldName;
    private final WorldType type;

    public WorldCreator(AriesWorld world) {
        this(world.getWorldName(), world.getType());
    }

    public WorldCreator(String worldName) {
        this(worldName, WorldType.VOID);
    }

    public WorldCreator(String worldName, WorldType type) {
        this.worldName = worldName;
        this.type = type;
    }

    public World generateWorld() {
        return this.type.getCreator().generateBukkitWorld(worldName);
    }

    public World loadWorld() {
        return this.type.getCreator().loadBukkitWorld(worldName);
    }

}
