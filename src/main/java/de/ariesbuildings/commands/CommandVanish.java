package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.commands.system.BaseCommand;
import de.ariesbuildings.commands.system.annotations.CommandArgs;
import de.ariesbuildings.commands.system.annotations.CommandPermission;
import de.ariesbuildings.commands.system.annotations.DefaultCommand;
import de.ariesbuildings.commands.system.annotations.UnknownCommand;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.objects.AriesPlayerManager;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandVanish extends BaseCommand {

    public CommandVanish(JavaPlugin plugin) {
        super(plugin, "vanish", "v");
        setDescription("Toggle vanish mode for a player");
    }

    @DefaultCommand
    @CommandArgs(0)
    @CommandPermission(Permission.PLAYER_OPTION_VANISH)
    private void onUsage(Player player) {
        AriesPlayer playerWorld = AriesPlayerManager.getPlayer(player);

        boolean currentValue = playerWorld.isOptionEnabled(PlayerOption.VANISH);
        playerWorld.setOption(PlayerOption.VANISH, !currentValue);
    }

    @UnknownCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    @UnknownCommand
    private void onUnknown(Player player) {
        if (!player.hasPermission(Permission.PLAYER_OPTION_VANISH)) {
            player.sendMessage(I18n.noPermission());
            return;
        }
        player.sendMessage(I18n.translate("command.unknown", "vanish", ""));
    }
}