package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.MainMenuGui;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMenu extends AriesCommand {

    public CommandMenu(JavaPlugin plugin) {
        super(plugin, "menu", "", "", "settings");
    }

    @Command
    private void onCommand(Player player) {
        new MainMenuGui().provide(player);
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }
}
