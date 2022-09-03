package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.objects.AriesPlayerManager;
import de.ariesbuildings.options.PlayerOption;
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
        AriesPlayer ariesPlayer = AriesPlayerManager.getPlayer(player);
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());

        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();


        if (!ariesPlayer.isOptionEnabled(PlayerOption.VANISH)) {
            event.setQuitMessage(I18n.translate("playerQuit", color + displayName + color + " §8| " + color + player.getName()));
        }
    }

}
