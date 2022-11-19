package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerWorldDamageListener implements Listener {

    @EventHandler
    public void handlePlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        AriesWorld ariesWorld = AriesSystem.getInstance().getWorldManager().getWorld(player.getWorld());

        if (ariesWorld == null) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(!ariesWorld.getOptions().isEnabled(WorldOption.PLAYER_DAMAGE));
    }

}
