package de.ariesbuildings.gui.provider;

import de.ariesbuildings.AriesPlayer;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.Scheduler;

@FunctionalInterface
public interface AriesProvider {

    void provide(AriesPlayer player);

    default void provide(AriesPlayer player, int value, BukkitUnit timeUnit) {
        Scheduler.delay(value, timeUnit, () -> provide(player));
    }

}
