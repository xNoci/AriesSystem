package de.ariesbuildings.listener;

import com.cryptomorin.xseries.XSound;
import com.google.common.collect.Lists;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.utils.ChatFormat;
import de.ariesbuildings.world.AriesWorld;
import me.noci.quickutilities.utils.formatter.FormatKey;
import me.noci.quickutilities.utils.formatter.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {

    private static final Pattern PING_PATTERN = Pattern.compile("(?<=\\s|^|\\W)(@)(?<name>[a-zA-Z0-9_]{2,16})\\b"); // Match @Username or ##@Username

    private static final String CHAT_FORMAT = "§8(§7" + ChatFormat.CURRENT_ARIES_WORLD + "§8) " + ChatFormat.RANK_COLOR + ChatFormat.RANK_DISPLAYNAME + " §8| " + ChatFormat.RANK_COLOR + FormatKey.PLAYER_NAME + "§8: §f" + FormatKey.MESSAGE;

    @EventHandler
    public void handleAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        RankInfo rankInfo = RankInfo.getInfo(player.getUniqueId());
        Optional<AriesWorld> world = AriesSystem.getInstance().getWorldManager().getWorld(player);

        event.setCancelled(true);

        String message = event.getMessage();
        message = message.replace("%", "%%");
        message = message.replace("\\", "\\\\");

        if (player.hasPermission(Permission.CHAT_USE_COLOR)) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        MessageFormatter formatter = MessageFormatter.format(CHAT_FORMAT);
        formatter.apply(ChatFormat.RANK_COLOR, rankInfo);
        formatter.apply(ChatFormat.RANK_DISPLAYNAME, rankInfo);
        formatter.apply(FormatKey.PLAYER_NAME, player);
        formatter.apply(FormatKey.MESSAGE, message);
        world.ifPresentOrElse(w -> formatter.apply(ChatFormat.CURRENT_ARIES_WORLD, w), () -> formatter.apply(ChatFormat.CURRENT_BUKKIT_WORLD, player.getWorld()));

        message = formatter.toString();
        Matcher matcher = PING_PATTERN.matcher(message);

        List<String> tags = Lists.newArrayList();
        if (player.hasPermission(Permission.CHAT_PING_PLAYER)) {
            while (matcher.find()) {
                tags.add(matcher.group("name"));
            }
        }
        
        String result = message;
        Bukkit.getOnlinePlayers().forEach(all -> {
            for (String tag : tags) {
                if (all.getName().equalsIgnoreCase(tag) && !all.getUniqueId().equals(player.getUniqueId())) {
                    all.sendMessage(replacePing(result, tag));
                    all.playSound(all.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1, 1);
                    return;
                }
            }
            all.sendMessage(result);
        });

        Bukkit.getConsoleSender().sendMessage(result);
    }

    public static String replacePing(String message, String name) {
        Pattern pingCapture = Pattern.compile("(?<=\\s|^|\\W)(@)(?<name>" + name + ")\\b");
        Matcher matcher = pingCapture.matcher(message);

        int lastIndex = 0;
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            output.append(message, lastIndex, matcher.start());
            output.append("§c§l@");
            output.append(matcher.group("name"));

            lastIndex = matcher.end();

            String before = message.substring(0, matcher.start());
            String chatColor = ChatColor.getLastColors(before);

            output.append("§r");
            output.append(chatColor);
        }

        if (lastIndex < message.length()) {
            output.append(message, lastIndex, message.length());
        }

        return output.toString();
    }

}
