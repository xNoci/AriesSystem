package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotations.CommandArgs;
import me.noci.quickutilities.quickcommand.annotations.CommandPermission;
import me.noci.quickutilities.quickcommand.annotations.Subcommand;
import me.noci.quickutilities.quickcommand.annotations.UnknownCommand;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandConfig extends AriesCommand {

    public CommandConfig(JavaPlugin plugin) {
        super(plugin, "config", "cfg");
    }

    @Subcommand("reload")
    @CommandArgs(0)
    @CommandPermission(Permission.CONFIG_RELOAD)
    public void reloadConfig(CommandSender sender) {
        AriesSystemConfig.load();
        sender.sendMessage(I18n.translate("command.config.reload_successfully"));
    }

    @Subcommand("display")
    @CommandArgs(0)
    @CommandPermission(Permission.CONFIG_DISPLAY)
    public void displayConfig(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.config.display_title"));

        for (Triple<String, String, String> entry : AriesSystemConfig.getAllEntries()) {
            sender.sendMessage(I18n.translate("command.config.display_entry", entry.getLeft(), entry.getMiddle(), entry.getRight()));
        }
    }

    @UnknownCommand
    public void unknownCommand(CommandSender sender) {
        if(sender.hasPermission(Permission.CONFIG_RELOAD) && sender.hasPermission("Permission.CONFIG_DISPLAY")) {
            sender.sendMessage(I18n.translate("command.unknown", "config", "<reload/display>"));
            return;
        }

        if(sender.hasPermission(Permission.CONFIG_RELOAD)) {
            sender.sendMessage(I18n.translate("command.unknown", "config", "<reload>"));
            return;
        }

        if(sender.hasPermission(Permission.CONFIG_DISPLAY)) {
            sender.sendMessage(I18n.translate("command.unknown", "config", "<display>"));
            return;
        }

        sender.sendMessage(I18n.translate("command.unknown", "config", ""));
    }

}
