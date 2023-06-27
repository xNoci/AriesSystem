package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.utils.ChatFormat;
import de.ariesbuildings.world.AriesWorld;
import me.noci.quickutilities.utils.formatter.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

public class PlayerChatListener implements Listener {

    private static final String CHAT_FORMAT = "§8(§7" + ChatFormat.CURRENT_ARIES_WORLD + "§8) " + ChatFormat.RANK_COLOR + ChatFormat.RANK_DISPLAYNAME + " §8| " + ChatFormat.RANK_COLOR + "%s§8: §f%s";

    @EventHandler
    public void handleAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());
        Optional<AriesWorld> world = AriesSystem.getInstance().getWorldManager().getWorld(player);

        String message = replaceMessage(player, event.getMessage());
        event.setMessage(message);

        MessageFormatter formatter = MessageFormatter.format(CHAT_FORMAT);
        formatter.apply(ChatFormat.RANK_COLOR, rankInfo);
        formatter.apply(ChatFormat.RANK_DISPLAYNAME, rankInfo);
        world.ifPresentOrElse(w -> formatter.apply(ChatFormat.CURRENT_ARIES_WORLD, w), () -> formatter.apply(ChatFormat.CURRENT_BUKKIT_WORLD, player.getWorld()));

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
