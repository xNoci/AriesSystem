package de.ariesbuildings.options;

import de.ariesbuildings.I18n;
import de.ariesbuildings.world.WorldStatus;
import de.ariesbuildings.world.WorldVisibility;
import lombok.Getter;

public enum WorldOption implements Option {

    ANTI_BLOCK_UPDATE(true, boolean.class),
    PLAYER_DAMAGE(false, boolean.class),
    ENTITY_TARGET_PLAYER(false, boolean.class),
    WEATHER_CYCLE(false, boolean.class),
    WORLD_VISIBILITY(WorldVisibility.PRIVATE, WorldVisibility.class),
    ALLOW_COMMAND_BLOCK(false, boolean.class),
    WORLD_STATUS(WorldStatus.CREATED, WorldStatus.class);

    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    <T> WorldOption(T defaultValue, Class<T> valueType) {
        this.name = I18n.translate("option.world." + name().toLowerCase() + ".name");
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public OptionType getType() {
        return OptionType.WORLD_OPTION;
    }


}
