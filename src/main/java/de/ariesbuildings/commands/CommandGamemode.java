package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.CommandPermission;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import me.noci.quickutilities.quickcommand.annotation.MatchNull;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandGamemode extends AriesCommand {

    public CommandGamemode(JavaPlugin plugin) {
        super(plugin, "gamemode", "Change the gamemode of a player.", "§cUsage: /gamemode <type> [player]", "gm");
    }

    @Command
    @CommandPermission({Permission.COMMAND_GAMEMODE, Permission.COMMAND_GAMEMODE_OTHER})
    private void onGamemodeChange(AriesPlayer player, GameMode gameMode) {
        if (gameMode == null) {
            playerFallback(player);
            return;
        }

        player.sendTranslate("command.gamemode.change", gameMode.name());
        player.getBase().setGameMode(gameMode);
    }

    @Command
    @CommandPermission(Permission.COMMAND_GAMEMODE_OTHER)
    private void onGamemodeChangeOther(AriesPlayer sender, GameMode gameMode, @MatchNull AriesPlayer target) {
        if (gameMode == null) {
            playerFallback(sender);
            return;
        }

        if (target == null) {
            sender.sendTranslate("command.player_not_found");
            return;
        }

        if (sender.getUUID().equals(target.getUUID())) {
            sender.sendTranslate("command.cannot_target_yourself");
            return;
        }

        sender.sendTranslate("command.gamemode.change_other", target.getName(), gameMode.name());
        target.sendTranslate("command.gamemode.change", gameMode.name());
        target.getBase().setGameMode(gameMode);
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    @FallbackCommand
    public void playerFallback(AriesPlayer player) {
        if (!player.hasPermission(Permission.COMMAND_GAMEMODE) && !player.hasPermission(Permission.COMMAND_GAMEMODE_OTHER)) {
            player.sendNoPermissions();
            return;
        }

        if (player.hasPermission(Permission.COMMAND_GAMEMODE_OTHER)) {
            player.sendTranslate("command.unknown", "gamemode", "<type> [player]");
            return;
        }

        player.sendTranslate("command.unknown", "gamemode", "<type>");
    }

}
