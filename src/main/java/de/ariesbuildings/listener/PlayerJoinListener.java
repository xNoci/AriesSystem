package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(I18n.translate("playerJoin", event.getPlayer().getName()));
    }

}
