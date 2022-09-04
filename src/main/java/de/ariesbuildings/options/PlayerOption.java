package de.ariesbuildings.options;

import lombok.Getter;
import org.bukkit.GameMode;

public enum PlayerOption implements Option {

    DEFAULT_GAMEMODE(0, "Default gamemode", GameMode.ADVENTURE, GameMode.class),
    GLOW(1, "Glow", true, boolean.class),
    VOID_DAMAGE_TELEPORT(2, "Void Damage Teleport", true, boolean.class),
    VANISH(3, "Vanish", false, boolean.class);

    @Getter private final int id;
    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    PlayerOption(int id, String name, Object defaultValue, Class<?> valueType) {
        this.id = id;
        this.name = name;
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public OptionType getType() {
        return OptionType.PLAYER_OPTION;
    }

}
