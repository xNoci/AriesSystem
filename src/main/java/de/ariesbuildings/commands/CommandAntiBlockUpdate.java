package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.commands.system.BaseCommand;
import de.ariesbuildings.commands.system.annotations.CommandPermission;
import de.ariesbuildings.commands.system.annotations.DefaultCommand;
import de.ariesbuildings.commands.system.annotations.Subcommand;
import de.ariesbuildings.commands.system.annotations.UnknownCommand;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAntiBlockUpdate extends BaseCommand {

    public CommandAntiBlockUpdate(JavaPlugin plugin) {
        super(plugin, "antiblockupdate", "abu");
        setDescription("Toggles block updates in a world");
    }

    @DefaultCommand
    @CommandPermission(Permission.ANTI_BLOCK_UPDATE)
    private void onUsage(Player player) {
        AriesWorld playerWorld = AriesWorld.getAriesWorld(player.getWorld());
        if (WorldOption.ANTI_BLOCK_UPDATE.getValueAsBoolean(playerWorld)) {
            WorldOption.ANTI_BLOCK_UPDATE.setValue(playerWorld, false);
            return;
        }
        WorldOption.ANTI_BLOCK_UPDATE.setValue(playerWorld, true);
    }

    @Subcommand("current")
    @CommandPermission(Permission.ANTI_BLOCK_UPDATE)
    private void onCheckCurrent(Player player) {
        AriesWorld playerWorld = AriesWorld.getAriesWorld(player.getWorld());
        player.sendMessage(I18n.translate("option.current", WorldOption.ANTI_BLOCK_UPDATE.getName(), WorldOption.ANTI_BLOCK_UPDATE.getValueAsString(playerWorld)));
    }

    @UnknownCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage("Only for players");
    }

    @UnknownCommand
    private void onUnknown(Player player) {
        if (!player.hasPermission(Permission.ANTI_BLOCK_UPDATE)) {
            player.sendMessage(I18n.noPermission());
            return;
        }
        player.sendMessage(I18n.translate("command.unknown", "abu", "[current]"));
    }
}
