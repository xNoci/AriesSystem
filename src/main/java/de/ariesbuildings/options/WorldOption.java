package de.ariesbuildings.options;

import lombok.Getter;

public enum WorldOption implements Option {

    ANTI_BLOCK_UPDATE("AntiBlockUpdate", true, boolean.class),
    PLAYER_DAMAGE("Player Damage", false, boolean.class);

    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    WorldOption(String name, Object defaultValue, Class<?> valueType) {
        this.name = name;
        this.valueType = valueType;
        this.defaultValue = defaultValue;
    }

    @Override
    public OptionType getType() {
        return OptionType.WORLD_OPTION;
    }

    @Override
    public Enum<? extends Option> getEnum() {
        return this;
    }

}
