package de.ariesbuildings.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import de.ariesbuildings.I18n;
import me.noci.quickutilities.quickcommand.QuickCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class AriesCommand extends QuickCommand {

    public AriesCommand(JavaPlugin plugin, String name, String description, String usage, String... aliases) {
        super(plugin, name, List.of(aliases), description, usage);
        noPermission = I18n.noPermission();
        noFallbackCommand = I18n.translate("command.failed_to_find_command", "%s");
    }

}
