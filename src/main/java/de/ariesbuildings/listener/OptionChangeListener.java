package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OptionChangeListener implements Listener {

    @EventHandler
    public void handleOptionChange(OptionChangeEvent event) {
        if (event.getOption() == PlayerOption.VANISH) {
            return;
        }

        if (event.isPlayerOption()) {
            AriesPlayer player = event.getPlayer();
            PlayerOption option = (PlayerOption) event.getOption();
            player.getBase().sendMessage(I18n.translate("option.player.changed", option.getName(), event.getNewValue()));
        }

        if (event.isWorldOption()) {
            AriesWorld world = event.getWorld();
            WorldOption option = (WorldOption) event.getOption();
            world.broadcast(I18n.translate("option.world.changed", option.getName(), event.getNewValue()));
        }
    }
}
