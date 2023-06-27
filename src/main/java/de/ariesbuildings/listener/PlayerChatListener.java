package de.ariesbuildings.listener;

import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.utils.ChatFormat;
import me.noci.quickutilities.utils.formatter.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private static final String CHAT_FORMAT = ChatFormat.RANK_COLOR.getKey() + ChatFormat.RANK_DISPLAYNAME + " §8| " + ChatFormat.RANK_COLOR + "%s§8: §f%s";

    @EventHandler
    public void handleAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());

        String message = replaceMessage(player, event.getMessage());
        event.setMessage(message);

        MessageFormatter formatter = MessageFormatter.format(CHAT_FORMAT);
        formatter.apply(ChatFormat.RANK_COLOR, rankInfo);
        formatter.apply(ChatFormat.RANK_DISPLAYNAME, rankInfo);
        event.setFormat(formatter.toString());
    }

    private String replaceMessage(Player player, String message) {
        message = message.replace("%", "%%");
        message = message.replace("\\", "\\\\");

        if (player.hasPermission(Permission.CHAT_USE_COLOR)) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        return message;
    }

}
