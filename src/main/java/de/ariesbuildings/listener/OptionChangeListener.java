package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OptionChangeListener implements Listener {

    @EventHandler
    public void handleOptionChange(OptionChangeEvent event) {
        if (event.isPlayerOption()) {
            Player player = event.getPlayer();
            PlayerOption option = (PlayerOption) event.getOption();
            player.sendMessage(I18n.translate("option.player.changed", option.getName(), event.getNewValue()));
            return;
        }

        if (event.isWorldOption()) {
            World world = event.getWorld();
            WorldOption option = (WorldOption) event.getOption();

            for (Player inWorld : world.getPlayers()) {
                inWorld.sendMessage(I18n.translate("option.world.changed", option.getName(), event.getNewValue()));
            }
            return;
        }

    }
}
