package de.ariesbuildings.world;

import de.ariesbuildings.AriesPlayer;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Optional;

public class RawLocation {

    private final AriesWorld ariesWorld;

    @Getter private final double x;
    @Getter private final double y;
    @Getter private final double z;

    @Getter private final float yaw;
    @Getter private final float pitch;

    public RawLocation(AriesWorld world, Location location) {
        this(world, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public RawLocation(Location location) {
        this(null, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public RawLocation(double x, double y, double z) {
        this(null, x, y, z, 0, 0);
    }

    public RawLocation(AriesWorld ariesWorld, double x, double y, double z, float yaw, float pitch) {
        this.ariesWorld = ariesWorld;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Optional<AriesWorld> getAriesWorld() {
        return Optional.ofNullable(ariesWorld);
    }

    public boolean teleportTo(AriesPlayer player) {
        var world = getAriesWorld();
        if (world.isEmpty()) return false;
        world.get().teleport(player, true);
        return true;
    }

}
