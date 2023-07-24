package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerWorldDamageListener implements Listener {

    @EventHandler
    public void handlePlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        AriesSystem.getInstance().getWorldManager()
                .getWorld(player.getWorld())
                .ifPresentOrElse(world -> {
                    event.setCancelled(world.getOptions().isDisabled(WorldOption.PLAYER_DAMAGE));
                }, () -> event.setCancelled(true));
    }

}
