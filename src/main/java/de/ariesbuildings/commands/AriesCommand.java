package de.ariesbuildings.commands;

import com.google.common.collect.ObjectArrays;
import de.ariesbuildings.I18n;
import me.noci.quickutilities.quickcommand.QuickCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AriesCommand extends QuickCommand {

    public AriesCommand(JavaPlugin plugin, String name, String... aliases) {
        super(plugin, name, aliases);
    }

    @Override
    protected String noPermission() {
        return I18n.noPermission();
    }

    @Override
    protected String commandNotFound(String commandName, String[] args) {
        return I18n.translate("command.failed_to_find_command", String.join(" ", ObjectArrays.concat(commandName, args)));
    }
}
