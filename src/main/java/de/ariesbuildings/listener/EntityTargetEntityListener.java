package de.ariesbuildings.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetEntityListener implements Listener {

    @EventHandler
    public void handleEntityTargetPlayer(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player)) return;
        event.setTarget(null);
    }

}
