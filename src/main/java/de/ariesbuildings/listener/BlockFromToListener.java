package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
    @EventHandler
    public void handleBlockFromTo(BlockFromToEvent event) {
        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(event.getBlock().getWorld());
        if (world.getOptions().isEnabled(WorldOption.ANTI_BLOCK_UPDATE)) event.setCancelled(true);
    }
}
