package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.VanishManager;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public static String getJoinMessage(AriesPlayer player) {
        RankInfo rankInfo = RankInfo.getInfo(player.getUUID());

        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();

        return I18n.translate("playerJoin", color + displayName + color + " §8| " + color + player.getBase().getName());
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());


        player.getBase().setPlayerListHeaderFooter(I18n.translate("tab_list.header"), I18n.translate("tab_list.footer"));

        player.getBase().setGlowing(player.isOptionEnabled(PlayerOption.GLOW));
        player.getBase().setGameMode(player.getOption(PlayerOption.DEFAULT_GAMEMODE, GameMode.class));


        if (player.isOptionDisabled(PlayerOption.VANISH)) {
            event.setJoinMessage(getJoinMessage(player));
        } else {
            event.setJoinMessage(null);
        }
    }

}
