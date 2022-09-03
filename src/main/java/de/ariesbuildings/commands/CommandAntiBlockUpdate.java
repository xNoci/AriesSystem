package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.commands.system.BaseCommand;
import de.ariesbuildings.commands.system.annotations.CommandPermission;
import de.ariesbuildings.commands.system.annotations.DefaultCommand;
import de.ariesbuildings.commands.system.annotations.Subcommand;
import de.ariesbuildings.commands.system.annotations.UnknownCommand;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.objects.AriesWorldManager;
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
    @CommandPermission(Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE)
    private void onUsage(Player player) {
        AriesWorld playerWorld = AriesWorldManager.getWorld(player.getWorld());

        boolean currentValue = playerWorld.isOptionEnabled(WorldOption.ANTI_BLOCK_UPDATE);
        playerWorld.setOption(WorldOption.ANTI_BLOCK_UPDATE, !currentValue);
    }

    @Subcommand("current")
    private void onCheckCurrent(Player player) {
        AriesWorld playerWorld = AriesWorldManager.getWorld(player.getWorld());
        player.sendMessage(I18n.translate("option.current", WorldOption.ANTI_BLOCK_UPDATE.getName(), playerWorld.getOption(WorldOption.ANTI_BLOCK_UPDATE, boolean.class)));
    }

    @UnknownCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    @UnknownCommand
    private void onUnknown(Player player) {
        if (!player.hasPermission(Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE)) {
            player.sendMessage(I18n.noPermission());
            return;
        }
        player.sendMessage(I18n.translate("command.unknown", "abu", "[current]"));
    }
}
