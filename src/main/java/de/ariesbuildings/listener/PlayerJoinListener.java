package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import de.ariesbuildings.permission.RankInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());
        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();

        //Check whether player has vanished enabled, only show join message if he isn't vanish
        event.setJoinMessage(I18n.translate("playerJoin", color + displayName + color + " §8| " + color + player.getName()));
    }

}
