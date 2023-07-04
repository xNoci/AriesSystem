package de.ariesbuildings.listener;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.OptionNotify;
import de.ariesbuildings.world.AriesWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OptionChangeListener implements Listener {

    @EventHandler
    public void handleOptionChange(OptionChangeEvent event) {
        if (event.isPlayerOption()) {
            AriesPlayer player = event.getPlayer();
            var notifyMode = player.getOptions().get(PlayerOption.NOTIFY_OPTION_CHANGE, OptionNotify.class);
            if (notifyMode != OptionNotify.ALWAYS && notifyMode != OptionNotify.ONLY_PLAYER) return;
            player.getBase().sendMessage(I18n.translate("option.player.changed", event.getOption().getName(), event.getNewValue()));
        }

        if (event.isWorldOption()) {
            AriesWorld world = event.getWorld();
            world.broadcast(I18n.translate("option.world.changed", event.getOption().getName(), event.getNewValue()), player -> {
                var notifyMode = player.getOptions().get(PlayerOption.NOTIFY_OPTION_CHANGE, OptionNotify.class);
                return notifyMode == OptionNotify.ALWAYS || notifyMode == OptionNotify.ONLY_WORLD;
            });
        }
    }
}
