package de.ariesbuildings.serializers;

import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import io.leangen.geantyref.TypeToken;
import org.bukkit.GameMode;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

public class AriesSerializers {

    public static TypeToken<OptionMap<PlayerOption>> PLAYER_OPTION_TYPE = new TypeToken<>() {
    };

    public static TypeToken<OptionMap<WorldOption>> WORLD_OPTION_TYPE = new TypeToken<>() {
    };

    public static TypeSerializerCollection SERIALIZERS = TypeSerializerCollection
            .builder()
            .register(GameMode.class, GameModeSerializer.INSTANCE)
            .register(PLAYER_OPTION_TYPE, PlayerOptionHolderSerializer.INSTANCE)
            .register(WORLD_OPTION_TYPE, WorldOptionHolderSerializer.INSTANCE)
            .build();

}
