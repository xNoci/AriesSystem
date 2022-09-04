package de.ariesbuildings.options;

import lombok.Getter;

public enum WorldOption implements Option {

    ANTI_BLOCK_UPDATE(0, "AntiBlockUpdate", true, boolean.class),
    PLAYER_DAMAGE(0, "Player Damage", false, boolean.class);

    @Getter private final int id;
    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    WorldOption(int id, String name, Object defaultValue, Class<?> valueType) {
        this.id = id;
        this.name = name;
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public OptionType getType() {
        return OptionType.WORLD_OPTION;
    }


}
