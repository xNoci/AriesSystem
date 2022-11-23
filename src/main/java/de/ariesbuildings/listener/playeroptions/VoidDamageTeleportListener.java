package de.ariesbuildings.listener.playeroptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.options.PlayerOption;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidDamageTeleportListener implements Listener {

    @EventHandler
    public void handlePlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(player);

        if (ariesPlayer.getOptions().isEnabled(PlayerOption.VOID_DAMAGE_TELEPORT) &&
                event.getCause() == EntityDamageEvent.DamageCause.VOID && player.getLocation().getY() < 0) {
            Location location = player.getLocation().clone();
            location.setY(100);
            player.teleport(location);
            player.sendMessage(I18n.translate("teleport.out_of_void.reason"));
            player.sendMessage(I18n.translate("teleport.out_of_void.help"));
        }
    }

}
