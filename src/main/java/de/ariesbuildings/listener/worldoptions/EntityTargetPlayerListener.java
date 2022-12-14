package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.world.AriesWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetPlayerListener implements Listener {

    @EventHandler
    public void handleEntityTargetPlayer(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player player)) return;

        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(player);

        if (world == null) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(!world.getOptions().isEnabled(WorldOption.ENTITY_TARGET_PLAYER));
    }

}
