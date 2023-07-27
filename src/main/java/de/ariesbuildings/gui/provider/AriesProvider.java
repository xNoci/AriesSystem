package de.ariesbuildings.gui.provider;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import me.noci.quickutilities.utils.BukkitUnit;
import org.bukkit.Bukkit;

@FunctionalInterface
public interface AriesProvider {
    
    void provide(AriesPlayer player);

    default void provide(AriesPlayer player, int value, BukkitUnit timeUnit) {
        Bukkit.getScheduler().runTaskLater(AriesSystem.getInstance(), () -> provide(player), timeUnit.toTicks(value));
    }

}
