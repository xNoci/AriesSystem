package de.ariesbuildings.world.creator;

import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldType;
import me.noci.quickutilities.utils.Require;
import org.bukkit.Bukkit;
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
        Require.checkState(Bukkit::isPrimaryThread, "World creator can only be executed on the primary thread.");
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
