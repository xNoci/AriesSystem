package de.ariesbuildings.options;

import de.ariesbuildings.world.WorldStatus;
import de.ariesbuildings.world.WorldVisibility;
import lombok.Getter;

public enum WorldOption implements Option {

    ANTI_BLOCK_UPDATE("AntiBlockUpdate", true, boolean.class),
    PLAYER_DAMAGE("Player Damage", false, boolean.class),
    ENTITY_TARGET_PLAYER("Entity Target Player", false, boolean.class),
    WEATHER_CYCLE("Weather cycle", false, boolean.class),
    WORLD_VISIBILITY("World visibility", WorldVisibility.PRIVATE, WorldVisibility.class),
    ALLOW_COMMAND_BLOCK("Allow command block", false, boolean.class),
    WORLD_STATUS("World status", WorldStatus.CREATED, WorldStatus.class);

    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    <T> WorldOption(String name, T defaultValue, Class<T> valueType) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public OptionType getType() {
        return OptionType.WORLD_OPTION;
    }


}
