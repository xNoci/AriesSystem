package de.ariesbuildings.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerLogin(PlayerLoginEvent event) {
        if(event.getResult() == PlayerLoginEvent.Result.KICK_FULL) event.allow();
    }

}
