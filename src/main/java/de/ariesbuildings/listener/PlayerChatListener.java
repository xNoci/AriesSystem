package de.ariesbuildings.listener;

import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.permission.RankInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private static final String CHAT_FORMAT = Format.COLOR.toString() + Format.RANK_DISPLAYNAME + " §8| " + Format.COLOR + Format.PLAYER_NAME + "§8: §f" + Format.MESSAGE;

    @EventHandler
    public void handleAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = replaceMessage(player, event.getMessage());
        message = formatMessage(player, message);

        event.setFormat(message);
    }

    private String replaceMessage(Player player, String message) {
        message = message.replace("%", "%%");
        message = message.replace("\\", "\\\\");
        if(player.hasPermission(Permission.CHAT_USE_COLOR)) {
            message = message.replace("&", "§");
        }

        return message;
    }

    private String formatMessage(Player player, String message) {
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());

        String format = CHAT_FORMAT;
        format = format.replaceAll(Format.COLOR.toString(), rankInfo.getColor().toString());
        format = format.replaceAll(Format.RANK_DISPLAYNAME.toString(), rankInfo.getDisplayname());
        format = format.replaceAll(Format.PLAYER_NAME.toString(), player.getName());
        format = format.replaceAll(Format.MESSAGE.toString(), message);

        return format;

    }

    private enum Format {

        COLOR("%c%"),
        RANK_DISPLAYNAME("%r_dspn%"),
        PLAYER_NAME("%name%"),
        MESSAGE("text");

        private final String replace;


        Format(String replace) {
            this.replace = replace;
        }

        @Override
        public String toString() {
            return replace;
        }
    }


}
