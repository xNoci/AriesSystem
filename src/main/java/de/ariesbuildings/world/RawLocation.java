package de.ariesbuildings.world;

import lombok.Getter;
import org.bukkit.Location;

public class RawLocation {

    @Getter private final double x;
    @Getter private final double y;
    @Getter private final double z;

    @Getter private final float yaw;
    @Getter private final float pitch;

    public RawLocation(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public RawLocation(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public RawLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
}
