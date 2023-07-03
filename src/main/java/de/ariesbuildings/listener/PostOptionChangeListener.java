package de.ariesbuildings.listener;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.events.PostOptionChangeEvent;
import de.ariesbuildings.managers.VanishManager;
import de.ariesbuildings.options.PlayerOption;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PostOptionChangeListener implements Listener {

    @EventHandler
    public void handlePostOptionChange(PostOptionChangeEvent event) {
        AriesPlayer player = event.getPlayer();

        if (event.getOption() == PlayerOption.VANISH) {
            boolean vanished = (boolean) event.getNewValue();

            VanishManager.sendActionbar();
            VanishManager.updatePlayerVisibility();

            if (vanished) {
                Bukkit.broadcastMessage(PlayerQuitListener.getQuitMessage(player));
            } else {
                Bukkit.broadcastMessage(PlayerJoinListener.getJoinMessage(player));
            }
        }

        if (event.getOption() == PlayerOption.FLY_SPEED) {
            player.setFlySpeed((int) event.getNewValue());
        }

    }

}
