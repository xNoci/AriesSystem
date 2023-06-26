package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.WorldImportResult;
import me.noci.quickutilities.quickcommand.annotations.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
public class CommandWorld extends AriesCommand {

    private final AriesWorldManager worldManager;

    public CommandWorld(JavaPlugin plugin, AriesWorldManager worldManager) {
        super(plugin, "worlds", "world");
        this.worldManager = worldManager;
        setDescription("Command to interact with the current world you are in.");
    }

    @DefaultCommand
    @CommandArgs(0)
    private void onUsage(Player player) {
        displayCurrentWorld(player);
    }

    @Subcommand("current")
    @CommandArgs(0)
    private void displayCurrentWorld(Player player) {
        worldManager.getWorld(player.getWorld())
                .ifPresentOrElse(world -> {
                    player.sendMessage(I18n.translate("command.world.current.display_name", world.getWorldName()));
                }, () -> {
                    player.sendMessage(I18n.translate("command.world.current.not_imported"));
                });
    }

    @Subcommand("physics")
    @CommandArgs(0)
    public void togglePhysics(Player player) {
        player.performCommand("antiblockupdate");
    }

    @Subcommand("import")
    @CommandPermission(Permission.WORLD_IMPORT)
    @CommandArgs(1)
    public void importWorld(CommandSender sender, String[] args) {
        String worldName = args[1];
        WorldImportResult result = worldManager.importWorld(worldName);

        if (result == WorldImportResult.SUCCESS) {
            sender.sendMessage(I18n.translate("command.world.import.success", worldName));
            return;
        }

        sender.sendMessage(I18n.translate("command.world.import.failed", worldName, result.name()));
    }

    @Subcommand("unimport")
    @CommandPermission(Permission.WORLD_UNIMPORT)
    @CommandArgs(1)
    public void unimportWorld(CommandSender sender, String[] args) {
        String worldName = args[1];
        WorldImportResult result = worldManager.unimportWorld(worldName);

        if (result == WorldImportResult.SUCCESS) {
            sender.sendMessage(I18n.translate("command.world.unimport.success", worldName));
            return;
        }

        sender.sendMessage(I18n.translate("command.world.unimport.failed", worldName, result.name()));
    }

    @Subcommand("tp")
    @CommandPermission(Permission.WORLD_TP)
    @CommandArgs(1)
    public void teleport(Player player, String[] args) {
        String worldName = args[1];
        worldManager.getWorld(worldName)
                .ifPresentOrElse(world -> {
                    world.teleport(player, true);
                }, () -> {
                    player.sendMessage(I18n.translate("world.not_found.world_name", worldName));
                });
    }

    @UnknownCommand
    private void unknownCommand(Player player) {
        player.sendMessage(I18n.translate("command.unknown", "worlds", ""));
    }

    @UnknownCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

}
