package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void lowestPlayerLoginHandler(PlayerLoginEvent event) {
        AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) event.allow();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void monitorPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.ALLOWED) return;
        AriesSystem.getInstance().getPlayerManager().removePlayer(event.getPlayer().getUniqueId());
    }

}
