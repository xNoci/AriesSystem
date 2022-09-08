package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {
    @EventHandler
    private void handleEntityChangeBlock(EntityChangeBlockEvent event) {
        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(event.getBlock().getWorld());
        if (world.isOptionEnabled(WorldOption.ANTI_BLOCK_UPDATE)) {
            if (event.getEntityType() != EntityType.FALLING_BLOCK || event.getTo() != Material.AIR) return;
            if (event.getBlock().getType() != Material.SAND) return;
            event.setCancelled(true);
            event.getBlock().getState().update(false, false);
        }
    }
}
