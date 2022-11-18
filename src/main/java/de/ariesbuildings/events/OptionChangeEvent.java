package de.ariesbuildings.events;

import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.Option;
import lombok.Getter;
import me.noci.quickutilities.events.core.CoreCancellableEvent;

public class OptionChangeEvent extends CoreCancellableEvent {

    @Getter private final Option option;
    @Getter private final Object oldValue;
    @Getter private final Object newValue;
    @Getter private final AriesPlayer player;
    @Getter private final AriesWorld world;

    public OptionChangeEvent(Option option, Object oldValue, Object newValue, AriesPlayer player) {
        this(option, oldValue, newValue, player, null);
    }

    public OptionChangeEvent(Option option, Object oldValue, Object newValue, AriesWorld world) {
        this(option, oldValue, newValue, null, world);
    }

    public OptionChangeEvent(Option option, Object oldValue, Object newValue, AriesPlayer player, AriesWorld world) {
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
