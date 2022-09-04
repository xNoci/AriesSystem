package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(player);
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());

        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();

        player.setPlayerListHeaderFooter(I18n.translate("tab_list.header"), I18n.translate("tab_list.footer"));

        player.setGlowing(ariesPlayer.isOptionEnabled(PlayerOption.GLOW));
        player.setGameMode(ariesPlayer.getOption(PlayerOption.DEFAULT_GAMEMODE, GameMode.class));


        if (ariesPlayer.isOptionDisabled(PlayerOption.VANISH)) {
            event.setJoinMessage(I18n.translate("playerJoin", color + displayName + color + " §8| " + color + player.getName()));
        } else {
            event.joinMessage(null);
        }
    }

}
