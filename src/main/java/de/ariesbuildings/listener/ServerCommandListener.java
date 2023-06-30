package de.ariesbuildings.listener;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.world.AriesWorld;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Optional;

public class ServerCommandListener implements Listener {


    @EventHandler
    private void handleCommandBlocks(ServerCommandEvent event) {
        CommandSender sender = event.getSender();
        Optional<AriesWorld> world = Optional.empty();
        boolean abort = true;

        if (sender instanceof BlockCommandSender blockCommandSender) {
            world = AriesSystem.getInstance().getWorldManager().getWorld(blockCommandSender.getBlock().getWorld());
            abort = false;
        }

        if (sender instanceof CommandMinecart commandMinecart) {
            world = AriesSystem.getInstance().getWorldManager().getWorld(commandMinecart.getWorld());
            abort = false;
        }

        if (abort) return;
        world.map(w -> w.getOptions().get(WorldOption.ALLOW_COMMAND_BLOCK, Boolean.class))
                .filter(allowCommands -> allowCommands)
                .ifPresentOrElse(w -> {}, () -> event.setCancelled(true));
    }
}
