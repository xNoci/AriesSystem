package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotations.CommandArgs;
import me.noci.quickutilities.quickcommand.annotations.CommandPermission;
import me.noci.quickutilities.quickcommand.annotations.DefaultCommand;
import me.noci.quickutilities.quickcommand.annotations.UnknownCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandVanish extends AriesCommand {

    public CommandVanish(JavaPlugin plugin) {
        super(plugin, "vanish", "v");
        setDescription("Toggle vanish mode for a player");
    }

    @DefaultCommand
    @CommandArgs(0)
    @CommandPermission(Permission.PLAYER_OPTION_VANISH)
    private void onUsage(Player p) {
        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(p);

        boolean currentValue = player.getOptions().isEnabled(PlayerOption.VANISH);
        player.getOptions().set(PlayerOption.VANISH, !currentValue);
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
