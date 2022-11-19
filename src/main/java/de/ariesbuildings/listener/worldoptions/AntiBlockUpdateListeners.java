package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class AntiBlockUpdateListeners implements Listener {

    @EventHandler
    private void handleEntityChangeBlock(EntityChangeBlockEvent event) {
        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(event.getBlock().getWorld());

        if (world == null || world.getOptions().isEnabled(WorldOption.ANTI_BLOCK_UPDATE)) {

            if (event.getEntityType() != EntityType.FALLING_BLOCK || event.getTo() != Material.AIR) return;
            if (event.getBlock().getType() != Material.SAND) return;

            event.setCancelled(true);
            event.getBlock().getState().update(false, false);
        }
    }


    @EventHandler
    public void handleBlockFromTo(BlockFromToEvent event) {
        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(event.getBlock().getWorld());

        if (world == null || world.getOptions().isEnabled(WorldOption.ANTI_BLOCK_UPDATE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockPhysics(BlockPhysicsEvent event) {
        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(event.getBlock().getWorld());

        if (world == null || world.getOptions().isEnabled(WorldOption.ANTI_BLOCK_UPDATE)) {
            event.setCancelled(true);
        }
    }

}
