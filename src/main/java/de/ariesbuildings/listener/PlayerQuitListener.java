package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(I18n.translate("playerQuit", event.getPlayer().getName()));
    }

}
