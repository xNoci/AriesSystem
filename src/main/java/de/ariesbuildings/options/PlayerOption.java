package de.ariesbuildings.options;

import de.ariesbuildings.I18n;
import lombok.Getter;
import org.bukkit.GameMode;

public enum PlayerOption implements Option {

    DEFAULT_GAMEMODE(GameMode.ADVENTURE, GameMode.class),
    GLOW(true, boolean.class),
    VOID_DAMAGE_TELEPORT(true, boolean.class),
    VANISH(false, boolean.class),
    FLY_SPEED(1, int.class),
    NOTIFY_OPTION_CHANGE(OptionNotify.ALWAYS, OptionNotify.class),
    PLAY_PING_SOUND(true, boolean.class),
    REMEMBER_LOCATION(false, boolean.class);

    @Getter private final String name;
    @Getter private final Object defaultValue;
    @Getter private final Class<?> valueType;

    <T> PlayerOption(T defaultValue, Class<T> valueType) {
        this.name = I18n.translate("option.player." + name().toLowerCase() + ".name");
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public OptionType getType() {
        return OptionType.PLAYER_OPTION;
    }

}
