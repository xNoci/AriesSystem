package de.ariesbuildings.options;

import lombok.Getter;
import org.bukkit.GameMode;

public enum PlayerOption implements Option {

    DEFAULT_GAMEMODE("Default gamemode", GameMode.ADVENTURE, GameMode.class),
    GLOW("Glow", true, boolean.class),
    VOID_DAMAGE_TELEPORT("Void Damage Teleport", true, boolean.class),
    VANISH("Vanish", false, boolean.class);

    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    PlayerOption(String name, Object defaultValue, Class<?> valueType) {
        this.name = name;
        this.valueType = valueType;
        this.defaultValue = defaultValue;
    }

    @Override
    public OptionType getType() {
        return OptionType.PLAYER_OPTION;
    }

    @Override
    public Enum<? extends Option> getEnum() {
        return this;
    }

}
