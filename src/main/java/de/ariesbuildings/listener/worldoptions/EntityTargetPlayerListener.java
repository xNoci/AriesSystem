package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetPlayerListener implements Listener {

    @EventHandler
    public void handleEntityTargetPlayer(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player player)) return;

        AriesSystem.getInstance().getWorldManager()
                .getWorld(player)
                .ifPresentOrElse(world -> {
                    event.setCancelled(!world.getOptions().isEnabled(WorldOption.ENTITY_TARGET_PLAYER));
                }, () -> event.setCancelled(true));
    }

}
