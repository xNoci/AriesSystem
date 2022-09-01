package de.ariesbuildings.events;

import de.ariesbuildings.options.Option;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OptionChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Option option;
    private final Player player;
    private final World world;
    private boolean cancelled;

    public OptionChangeEvent(Option option, Player player) {
        this.option = option;
        this.player = player;
        this.world = null;
        cancelled = false;
    }

    public OptionChangeEvent(Option option, World world) {
        this.option = option;
        this.world = world;
        this.player = null;
        cancelled = false;
    }

    public Option getOption() {
        return option;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
