package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.locales.LanguageLoader;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.CommandPermission;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandReloadLocals extends AriesCommand {

    public CommandReloadLocals(JavaPlugin plugin) {
        super(plugin, "reloadlocals", "", "", "reloadlanguage", "rll");
    }

    @Command
    @CommandPermission(Permission.LANGUAGE_RELOAD)
    private void reloadLanguage(CommandSender sender) {
        LanguageLoader.loadLocals();
        sender.sendMessage(I18n.translate("command.reload_locals.success"));
    }

    @FallbackCommand
    private void reloadLangauge(CommandSender sender) {
        if (!sender.hasPermission(Permission.LANGUAGE_RELOAD)) {
            sender.sendMessage(I18n.noPermission());
            return;
        }

        sender.sendMessage(I18n.translate("command.unknown", "reloadlocales"));
    }

}
