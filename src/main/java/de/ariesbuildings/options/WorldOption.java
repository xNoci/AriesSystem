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
    WORLD_STATUS(WorldStatus.CREATED, WorldStatus.class),
    ALLOW_HOSTILE_SPAWNING(false, boolean.class),
    ALLOW_FRIENDLY_SPAWNING(false, boolean.class);

    @Getter private final String name;
    @Getter private final String description;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    <T> WorldOption(T defaultValue, Class<T> valueType) {
        this.name = I18n.tryTranslate("option.world." + name().toLowerCase() + ".name").orElse(name().toLowerCase());
        this.description = I18n.tryTranslate("option.world." + name().toLowerCase() + ".description").orElse("");
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public OptionType getType() {
        return OptionType.WORLD_OPTION;
    }


}
