package de.ariesbuildings.listener;

import com.google.common.collect.Lists;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.hooks.WorldEditHook;
import de.ariesbuildings.utils.BlockedCommand;
import de.ariesbuildings.utils.WorldEditBlockedCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class PlayerCommandPreprocessListener implements Listener {

    private final List<BlockedCommand> blockedCommandList = Lists.newArrayList();

    public PlayerCommandPreprocessListener() {
        if (WorldEditHook.isEnabled()) {
            AriesSystemConfig.debug("Added WorldEdit commands to blocked commands list.'");
            blockedCommandList.addAll(WorldEditBlockedCommand.BLOCKED_COMMAND_LIST);
        }
    }

    @EventHandler
    public void handlePlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;

        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());
        String command = event.getMessage().split(" ")[0];

        blockedCommandList.stream()
                .filter(blockedCommand -> blockedCommand.matches(command))
                .filter(blockedCommand -> blockedCommand.shouldBlock(player))
                .findFirst()
                .ifPresent(blockedCommand -> {
                    event.setCancelled(true);
                    player.sendTranslate(blockedCommand.getReasonKey());
                });
    }


}
