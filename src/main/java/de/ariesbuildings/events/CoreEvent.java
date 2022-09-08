package de.ariesbuildings.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoreEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public CoreEvent() {
        this(false);
    }

    public CoreEvent(boolean async) {
        super(async);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}
