package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.CommandPermission;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandVanish extends AriesCommand {

    public CommandVanish(JavaPlugin plugin) {
        super(plugin, "vanish", "Toggle vanish mode for a player", "", "v");
    }

    @Command
    @CommandPermission(Permission.PLAYER_OPTION_VANISH)
    private void onUsage(AriesPlayer player) {
        boolean currentValue = player.getOptions().isEnabled(PlayerOption.VANISH);
        player.getOptions().set(PlayerOption.VANISH, !currentValue);
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    @FallbackCommand
    private void onUnknown(AriesPlayer player) {
        if (!player.hasPermission(Permission.PLAYER_OPTION_VANISH)) {
            player.sendMessage(I18n.noPermission());
            return;
        }
        player.sendMessage(I18n.translate("command.unknown", "vanish", ""));
    }
}
