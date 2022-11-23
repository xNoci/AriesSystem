package de.ariesbuildings.config.serializers;

import org.bukkit.GameMode;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;

public class GameModeSerializer implements TypeSerializer<GameMode> {

    static final GameModeSerializer INSTANCE = new GameModeSerializer();

    private static final String GAMEMODE = "gamemode";

    private GameModeSerializer() {
    }

    private ConfigurationNode nonVirtualNode(final ConfigurationNode source, final Object... path) throws SerializationException {
        if (!source.hasChild(path)) {
            throw new SerializationException("Required field " + Arrays.toString(path) + " was not present in node");
        }
        return source.node(path);
    }

    @Override
    public GameMode deserialize(Type type, ConfigurationNode source) throws SerializationException {
        ConfigurationNode gamemodeNode = nonVirtualNode(source, GAMEMODE);
        return GameMode.valueOf(gamemodeNode.getString());
    }

    @Override
    public void serialize(Type type, @Nullable GameMode gamemode, ConfigurationNode target) throws SerializationException {
        if (gamemode == null) return;
        target.node(GAMEMODE).set(gamemode.name());
    }
}
