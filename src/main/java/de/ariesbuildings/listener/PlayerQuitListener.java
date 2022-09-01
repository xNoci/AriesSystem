package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import de.ariesbuildings.permission.RankInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());
        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();

        //Check whether player has vanished enabled, only show quit message if he isn't vanish
        event.setQuitMessage(I18n.translate("playerQuit", color + displayName + color + " §8| " + color + player.getName()));
    }

}
