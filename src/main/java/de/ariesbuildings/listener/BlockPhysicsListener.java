package de.ariesbuildings.listener;

import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsListener implements Listener {
    @EventHandler
    public void handleBlockPhysics(BlockPhysicsEvent event) {
        AriesWorld world = AriesWorldManager.getWorld(event.getBlock().getWorld());
        if (world.isOptionEnabled(WorldOption.ANTI_BLOCK_UPDATE)) event.setCancelled(true);
    }
}
