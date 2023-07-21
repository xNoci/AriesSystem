package de.ariesbuildings.config.serializers;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.RawLocation;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;

public class RawLocationSerializer implements TypeSerializer<RawLocation> {

    static final RawLocationSerializer INSTANCE = new RawLocationSerializer();

    private static final String LOCATION = "rawLocation";

    private RawLocationSerializer() {
    }

    private ConfigurationNode nonVirtualNode(final ConfigurationNode source, final Object... path) throws SerializationException {
        if (!source.hasChild(path)) {
            throw new SerializationException("Required field " + Arrays.toString(path) + " was not present in node");
        }
        return source.node(path);
    }

    @Override
    public RawLocation deserialize(Type type, ConfigurationNode source) throws SerializationException {
        ConfigurationNode location = nonVirtualNode(source, LOCATION);

        AriesWorld world = null;
        ConfigurationNode ariesWorldNode = location.node("ariesWorld");
        if (!ariesWorldNode.virtual() && ariesWorldNode.getString() != null) {
            world = AriesSystem.getInstance().getWorldManager().getWorld(ariesWorldNode.getString()).get();
        }

        double x = location.node("x").getDouble();
        double y = location.node("y").getDouble();
        double z = location.node("z").getDouble();
        float yaw = location.node("yaw").getFloat();
        float pitch = location.node("pitch").getFloat();

        return new RawLocation(world, x, y, z, yaw, pitch);
    }

    @Override
    public void serialize(Type type, @Nullable RawLocation location, ConfigurationNode target) throws SerializationException {
        if (location == null) return;

        var ariesWorld = location.getAriesWorld();
        if (ariesWorld.isPresent()) {
            target.node(LOCATION).node("ariesWorld").set(ariesWorld.get().getWorldName());
        }

        target.node(LOCATION).node("x").set(location.getX());
        target.node(LOCATION).node("y").set(location.getY());
        target.node(LOCATION).node("z").set(location.getZ());
        target.node(LOCATION).node("yaw").set(location.getYaw());
        target.node(LOCATION).node("pitch").set(location.getPitch());
    }
}
