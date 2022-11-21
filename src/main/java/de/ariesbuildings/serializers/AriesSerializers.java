package de.ariesbuildings.serializers;

import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import io.leangen.geantyref.TypeToken;
import org.bukkit.GameMode;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.util.List;
import java.util.UUID;

public class AriesSerializers {

    public static TypeSerializerCollection SERIALIZERS = TypeSerializerCollection
            .builder()
            .register(GameMode.class, GameModeSerializer.INSTANCE)
            .register(Type.PLAYER_OPTION_MAP, PlayerOptionMapSerializer.INSTANCE)
            .register(Type.WORLD_OPTION_MAP, WorldOptionMapSerializer.INSTANCE)
            .build();

    public static class Type {

        public static TypeToken<OptionMap<PlayerOption>> PLAYER_OPTION_MAP = new TypeToken<>() {
        };

        public static TypeToken<OptionMap<WorldOption>> WORLD_OPTION_MAP = new TypeToken<>() {
        };

        public static TypeToken<List<UUID>> UUID_LIST = new TypeToken<>() {
        };

    }

}
