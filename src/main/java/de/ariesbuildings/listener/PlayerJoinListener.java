package de.ariesbuildings.listener;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.VanishManager;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.world.RawLocation;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public static String getJoinMessage(AriesPlayer player) {
        RankInfo rankInfo = player.getRankInfo();

        ChatColor color = rankInfo.getColor();
        String displayName = rankInfo.getDisplayname();

        return I18n.translate("playerJoin", color + displayName + color + " §8| " + color + player.getBase().getName());
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());

        player.updateFlySpeed();
        player.getBase().setPlayerListHeaderFooter(I18n.translate("tab_list.header"), I18n.translate("tab_list.footer"));

        player.getBase().setGlowing(player.getOptions().isEnabled(PlayerOption.GLOW));
        player.getBase().setGameMode(player.getOptions().get(PlayerOption.DEFAULT_GAMEMODE, GameMode.class));


        if (player.getOptions().isDisabled(PlayerOption.VANISH)) {
            event.setJoinMessage(getJoinMessage(player));
        } else {
            event.setJoinMessage(null);
        }

        VanishManager.updatePlayerVisibility();

        boolean rememberLocation = player.getOptions().isEnabled(PlayerOption.REMEMBER_LOCATION);
        RawLocation rawLocation = player.getLastKnownLocation();
        boolean teleported = false;

        if (rememberLocation && rawLocation != null) {
            teleported = rawLocation.teleportTo(player);
        }

        if (!rememberLocation || !teleported) {
            AriesSystem.getInstance().teleportToSpawnWorld(player);
        }
    }

}
