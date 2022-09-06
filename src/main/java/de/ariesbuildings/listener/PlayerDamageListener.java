package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void handlePlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(player);
        AriesWorld ariesWorld = AriesSystem.getInstance().getWorldManager().getWorld(player.getWorld());

        if (ariesPlayer.isOptionEnabled(PlayerOption.VOID_DAMAGE_TELEPORT) &&
                event.getCause() == EntityDamageEvent.DamageCause.VOID && player.getLocation().getY() < 0) {
            Location location = player.getLocation().clone();
            location.setY(100);
            player.teleport(location);
            player.sendMessage(I18n.translate("teleport.out_of_void.reason"));
            player.sendMessage(I18n.translate("teleport.out_of_void.help"));
        }

        if (ariesWorld != null) {
            event.setCancelled(!ariesWorld.isOptionEnabled(WorldOption.PLAYER_DAMAGE));
        }
    }
}
