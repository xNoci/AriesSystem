package de.ariesbuildings.commands;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.objects.AriesWorld;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotations.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAntiBlockUpdate extends AriesCommand {

    public CommandAntiBlockUpdate(JavaPlugin plugin) {
        super(plugin, "antiblockupdate", "abu");
        setDescription("Toggles block updates in a world");
    }

    @DefaultCommand
    @CommandArgs(0)
    @CommandPermission(Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE)
    private void onUsage(Player player) {
        AriesWorld playerWorld = AriesSystem.getInstance().getWorldManager().getWorld(player.getWorld());

        if (!playerWorld.hasWorldPermission(player)) {
            player.sendMessage(I18n.translate("noPermission.not_a_builder"));
            return;
        }

        boolean currentValue = playerWorld.getOptions().isEnabled(WorldOption.ANTI_BLOCK_UPDATE);
        playerWorld.getOptions().set(WorldOption.ANTI_BLOCK_UPDATE, !currentValue);
    }

    @Subcommand("current")
    @CommandArgs(0)
    private void onCheckCurrent(Player player) {
        AriesWorld playerWorld = AriesSystem.getInstance().getWorldManager().getWorld(player.getWorld());
        player.sendMessage(I18n.translate("option.current", WorldOption.ANTI_BLOCK_UPDATE.getName(), playerWorld.getOptions().get(WorldOption.ANTI_BLOCK_UPDATE, boolean.class)));
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
