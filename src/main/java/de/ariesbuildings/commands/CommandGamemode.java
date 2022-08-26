package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.commands.system.BaseCommand;
import de.ariesbuildings.commands.system.annotations.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class CommandGamemode extends BaseCommand {

    public CommandGamemode(JavaPlugin plugin) {
        super(plugin, "gamemode", "gm");
        setDescription("Change the gamemode of a player.");
        setUsage("§cUsage: /gamemode <type> [player]");
    }

    @DefaultCommand
    @CommandArgs(1)
    @CommandPermission("aries.gamemode")
    private void onGamemodeChange(Player player, String[] args) {
        Optional<GameMode> gameMode = parseGamemode(args[0]);

        if (gameMode.isEmpty()) {
            playerFallback(player);
            return;
        }

        player.sendMessage(I18n.translate("command.gamemode.change", gameMode.get().name()));
        player.setGameMode(gameMode.get());
    }

    @Subcommand("*")
    @CommandArgs(1)
    @CommandPermission("aries.gamemode.other")
    private void onGamemodeChangeOther(Player player, String[] args) {
        Optional<GameMode> gameMode = parseGamemode(args[0]);

        if (gameMode.isEmpty()) {
            playerFallback(player);
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(I18n.translate("command.player_not_found", args[1]));
            return;
        }

        if (player.getUniqueId().equals(target.getUniqueId())) {
            player.sendMessage(I18n.translate("command.cannot_target_yourself", args[1]));
            return;
        }

        player.sendMessage(I18n.translate("command.gamemode.change_other", target.getName(), gameMode.get().name()));
        target.sendMessage(I18n.translate("command.gamemode.change", gameMode.get().name()));
        target.setGameMode(gameMode.get());
    }

    @UnknownCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    @UnknownCommand
    public void playerFallback(Player player) {
        if (!player.hasPermission("aries.gamemode") && !player.hasPermission("aries.gamemode.other")) {
            player.sendMessage(I18n.noPermission());
            return;
        }

        if (player.hasPermission("aries.gamemode.other")) {
            player.sendMessage(I18n.translate("command.unknown", "gamemode", "<type> [player]"));
            return;
        }

        player.sendMessage(I18n.translate("command.unknown", "gamemode", "<type>"));
    }


    private Optional<GameMode> parseGamemode(String string) {
        int gamemodeID = 999;

        try {
            gamemodeID = Integer.parseInt(string);
            if (gamemodeID <= -1)
                gamemodeID = 999;
        } catch (NumberFormatException ignored) {
        }

        GameMode output = GameMode.getByValue(gamemodeID);
        if (output != null)
            return Optional.of(output);

        switch (string.toLowerCase()) {
            case "s", "survival" -> output = GameMode.SURVIVAL;
            case "c", "creative" -> output = GameMode.CREATIVE;
            case "a", "adventure" -> output = GameMode.ADVENTURE;
            case "sp", "spec", "spectator" -> output = GameMode.SPECTATOR;
        }

        return Optional.ofNullable(output);
    }

}
