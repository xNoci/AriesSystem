package de.ariesbuildings.listener.worldoptions;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class AntiBlockUpdateListeners implements Listener {

    @EventHandler
    private void handleEntityChangeBlock(EntityChangeBlockEvent event) {
        AriesSystem.getInstance().getWorldManager()
                .getWorld(event.getBlock().getWorld())
                .filter(world -> world.getOptions().isDisabled(WorldOption.ANTI_BLOCK_UPDATE))
                .ifPresentOrElse(world -> { /*ANTI BLOCK UPDATES DISABLED - IGNORE IT*/ }, () -> {
                    // ANTI BLOCK UPDATES ENABLED OR WORLD IS NULL - STOP UPDATES
                    if (event.getEntityType() != EntityType.FALLING_BLOCK || event.getTo() != XMaterial.AIR.parseMaterial())
                        return;
                    if (event.getBlock().getType() != XMaterial.SAND.parseMaterial()) return;

                    event.setCancelled(true);
                    event.getBlock().getState().update(false, false);
                });
    }


    @EventHandler
    public void handleBlockFromTo(BlockFromToEvent event) {
        AriesSystem.getInstance().getWorldManager()
                .getWorld(event.getBlock().getWorld())
                .filter(world -> world.getOptions().isDisabled(WorldOption.ANTI_BLOCK_UPDATE))
                .ifPresentOrElse(world -> event.setCancelled(false), () -> event.setCancelled(true));
    }

    @EventHandler
    public void handleBlockPhysics(BlockPhysicsEvent event) {
        AriesSystem.getInstance().getWorldManager()
                .getWorld(event.getBlock().getWorld())
                .filter(world -> world.getOptions().isDisabled(WorldOption.ANTI_BLOCK_UPDATE))
                .ifPresentOrElse(world -> event.setCancelled(false), () -> event.setCancelled(true));
    }

}
