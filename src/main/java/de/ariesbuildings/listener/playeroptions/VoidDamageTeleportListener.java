package de.ariesbuildings.listener.playeroptions;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.PlayerOption;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidDamageTeleportListener implements Listener {

    @EventHandler
    public void handlePlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer((Player) event.getEntity());

        if (player.getOptions().isEnabled(PlayerOption.VOID_DAMAGE_TELEPORT) &&
                event.getCause() == EntityDamageEvent.DamageCause.VOID && player.getLocation().getY() < 0) {
            Location location = player.getLocation().clone();
            location.setY(100);
            player.teleport(location);
            player.sendTranslate("teleport.out_of_void.reason");
            player.sendTranslate("teleport.out_of_void.help");
        }
    }

}
