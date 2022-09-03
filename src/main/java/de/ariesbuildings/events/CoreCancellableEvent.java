package de.ariesbuildings.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

public class CoreCancellableEvent extends CoreEvent implements Cancellable {

    @Getter @Setter private boolean cancelled;

    public CoreCancellableEvent(boolean cancelled) {
        this(false, cancelled);
    }

    public CoreCancellableEvent(boolean async, boolean cancelled) {
        super(async);
        this.cancelled = cancelled;
    }

}