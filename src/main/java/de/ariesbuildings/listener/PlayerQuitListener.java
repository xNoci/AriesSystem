package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    public static String getQuitMessage(AriesPlayer player) {
        RankInfo rankInfo = player.getRankInfo();

        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();

        return I18n.translate("playerQuit", color + displayName + color + " §8| " + color + player.getBase().getName());
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());

        if (ariesPlayer.isOptionDisabled(PlayerOption.VANISH)) {
            event.setQuitMessage(getQuitMessage(ariesPlayer));
        } else {
            event.setQuitMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void monitorPlayerQuit(PlayerQuitEvent event) {
        AriesSystem.getInstance().getPlayerManager().removeUser(event.getPlayer().getUniqueId());
    }

}
