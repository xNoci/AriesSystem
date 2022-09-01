package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OptionChangeListener implements Listener {
    @EventHandler
    public void handleOptionChange(OptionChangeEvent event) {
        if(event.getOption().getType() == Option.OptionType.PLAYER_OPTION) {
            Player player = event.getPlayer();
            PlayerOption option = (PlayerOption) event.getOption();
            player.sendMessage(I18n.translate("option.player.changed", option.getName(), option.getValueAsString(AriesPlayer.getAriesPlayer(player))));
            return;
        }
        World world = event.getWorld();
        WorldOption option = (WorldOption) event.getOption();
        for(Player inWorld : world.getPlayers()) {
            inWorld.sendMessage(I18n.translate("option.world.changed", option.getName(), option.getValueAsString(AriesWorld.getAriesWorld(world))));
        }
    }
}
