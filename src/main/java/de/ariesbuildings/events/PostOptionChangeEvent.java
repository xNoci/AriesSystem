package de.ariesbuildings.events;

import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.Option;

public class PostOptionChangeEvent extends OptionChangeEvent {

    public PostOptionChangeEvent(Option option, Object oldValue, Object newValue, AriesPlayer player) {
        super(option, oldValue, newValue, player, null);
    }

    public PostOptionChangeEvent(Option option, Object oldValue, Object newValue, AriesWorld world) {
        super(option, oldValue, newValue, null, world);
    }

    public PostOptionChangeEvent(Option option, Object oldValue, Object newValue, AriesPlayer player, AriesWorld world) {
        super(option, oldValue, newValue, player, world);
    }

}
