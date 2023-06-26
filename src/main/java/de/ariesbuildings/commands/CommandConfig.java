package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AbstractConfig;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.config.ConfigEntryValue;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotations.*;
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

        for (ConfigEntryValue entry : AbstractConfig.getEntries(AriesSystemConfig.class)) {
            sender.sendMessage(I18n.translate("command.config.display_entry", entry.fieldName(), entry.configPath(), entry.configValue()));
        }
    }

    @DefaultCommand
    @UnknownCommand
    public void unknownCommand(CommandSender sender) {
        if (sender.hasPermission(Permission.CONFIG_RELOAD) && sender.hasPermission("Permission.CONFIG_DISPLAY")) {
            sender.sendMessage(I18n.translate("command.unknown", "config", "<reload/display>"));
            return;
        }

        if (sender.hasPermission(Permission.CONFIG_RELOAD)) {
            sender.sendMessage(I18n.translate("command.unknown", "config", "<reload>"));
            return;
        }

        if (sender.hasPermission(Permission.CONFIG_DISPLAY)) {
            sender.sendMessage(I18n.translate("command.unknown", "config", "<display>"));
            return;
        }

        sender.sendMessage(I18n.noPermission());
    }

}
