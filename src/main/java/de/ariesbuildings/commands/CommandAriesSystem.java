package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAriesSystem extends AriesCommand {

    public CommandAriesSystem(JavaPlugin plugin) {
        super(plugin, "ariessystem", "", "", "aries");
    }

    @Command
    @FallbackCommand
    private void showVersion(CommandSender sender) {
        JavaPlugin plugin = getPlugin();
        sender.sendMessage(I18n.translate("command.ariessystem.current_version", plugin.getName(), plugin.getDescription().getVersion()));
    }

}
