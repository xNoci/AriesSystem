package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.CommandPermission;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import me.noci.quickutilities.quickcommand.annotation.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAntiBlockUpdate extends AriesCommand {

    public CommandAntiBlockUpdate(JavaPlugin plugin) {
        super(plugin, "antiblockupdate", "Toggles block updates in a world", "physics", "abu");
    }

    @Command
    @CommandPermission(Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE)
    private void onUsage(Player player) {
        AriesSystem.getInstance().getWorldManager()
                .getWorld(player.getWorld())
                .ifPresentOrElse(world -> {
                    if (!world.hasWorldPermission(player, Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE)) {
                        player.sendMessage(I18n.translate("noPermission.not_a_builder"));
                        return;
                    }

                    boolean currentValue = world.getOptions().isEnabled(WorldOption.ANTI_BLOCK_UPDATE);
                    world.getOptions().set(WorldOption.ANTI_BLOCK_UPDATE, !currentValue);
                }, () -> {
                    player.sendMessage(I18n.translate("world.not_found.current_world"));
                });
    }

    @SubCommand(path = "current")
    private void onCheckCurrent(Player player) {
        AriesSystem.getInstance().getWorldManager()
                .getWorld(player.getWorld())
                .ifPresentOrElse(world -> {
                    String message = I18n.translate("option.current", WorldOption.ANTI_BLOCK_UPDATE.getName(), world.getOptions().get(WorldOption.ANTI_BLOCK_UPDATE, boolean.class));
                    player.sendMessage(message);
                }, () -> {
                    player.sendMessage(I18n.translate("world.not_found.current_world"));
                });
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    @FallbackCommand
    private void onUnknown(AriesPlayer player) {
        if (!player.hasPermission(Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE)) {
            player.sendMessage(I18n.noPermission());
            return;
        }
        player.sendMessage(I18n.translate("command.unknown", "abu", "[current]"));
    }
}
