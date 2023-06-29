package de.ariesbuildings.options;

import lombok.Getter;

public enum WorldOption implements Option {

    ANTI_BLOCK_UPDATE("AntiBlockUpdate", true, boolean.class),
    PLAYER_DAMAGE("Player Damage", false, boolean.class),
    ENTITY_TARGET_PLAYER("Entity Target Player", false, boolean.class),
    WEATHER_CYCLE("Weather cycle", false, boolean.class);
    //TODO Visibility?
    //TODO Status - WAITING, WIP, FINISHED, REWORK

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
