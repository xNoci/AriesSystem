package de.ariesbuildings.events;

import de.ariesbuildings.options.Option;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class OptionChangeEvent extends CoreCancellableEvent {


    @Getter private final Option option;
    @Getter private final Object oldValue;
    @Getter private final Object newValue;
    @Getter private final Player player;
    @Getter private final World world;

    public OptionChangeEvent(Option option, Object oldValue, Object newValue, Player player) {
        this(option, oldValue, newValue, player, null);
    }

    public OptionChangeEvent(Option option, Object oldValue, Object newValue, World world) {
        this(option, oldValue, newValue, null, world);
    }

    public OptionChangeEvent(Option option, Object oldValue, Object newValue, Player player, World world) {
        super(false);
        this.option = option;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.player = player;
        this.world = world;
    }

    public boolean isWorldOption() {
        return this.option.getType() == Option.OptionType.WORLD_OPTION;
    }

    public boolean isPlayerOption() {
        return this.option.getType() == Option.OptionType.PLAYER_OPTION;
    }

}
