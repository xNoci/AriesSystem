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
        if (event.getOption() instanceof PlayerOption playerOption) {
            AriesPlayer player = event.getPlayer();
            switch (playerOption) {
                case VANISH -> handleVanish(player, (Boolean) event.getNewValue());
                case FLY_SPEED -> player.setFlySpeed((int) event.getNewValue());
                case GLOW -> player.getBase().setGlowing((Boolean) event.getNewValue());
            }
        }
    }

    private void handleVanish(AriesPlayer player, boolean vanished) {
        VanishManager.sendActionbar();
        VanishManager.updatePlayerVisibility();
        String message = vanished ? PlayerQuitListener.getQuitMessage(player) : PlayerJoinListener.getJoinMessage(player);
        Bukkit.broadcastMessage(message);
    }

}
