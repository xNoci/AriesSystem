package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.WorldImportResult;
import de.ariesbuildings.world.WorldType;
import me.noci.quickutilities.quickcommand.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CommandWorld extends AriesCommand {

    private final AriesWorldManager worldManager;

    public CommandWorld(JavaPlugin plugin, AriesWorldManager worldManager) {
        super(plugin, "worlds", "Command to interact with the current world you are in.", "", "world");
        this.worldManager = worldManager;
    }

    @Command
    private void onUsage(Player player) {
        displayCurrentWorld(player);
    }

    @SubCommand(path = "current")
    private void displayCurrentWorld(Player player) {
        worldManager.getWorld(player.getWorld())
                .ifPresentOrElse(world -> {
                    player.sendMessage(I18n.translate("command.world.current.display_name", world.getWorldName()));
                }, () -> {
                    player.sendMessage(I18n.translate("command.world.current.not_imported"));
                });
    }

    @SubCommand(path = "physics")
    public void togglePhysics(Player player) {
        player.performCommand("antiblockupdate");
    }

    @SubCommand(path = "import")
    @CommandPermission(Permission.WORLD_IMPORT)
    public void importWorld(CommandSender sender, String worldName) {
        WorldImportResult result = worldManager.importWorld(worldName);

        if (result == WorldImportResult.SUCCESS) {
            sender.sendMessage(I18n.translate("command.world.import.success", worldName));
            return;
        }

        sender.sendMessage(I18n.translate("command.world.import.failed", worldName, result.name()));
    }

    @SubCommand(path = "unimport")
    @CommandPermission(Permission.WORLD_UNIMPORT)
    public void unimportWorld(CommandSender sender, String worldName) {
        WorldImportResult result = worldManager.unimportWorld(worldName);

        if (result == WorldImportResult.SUCCESS) {
            sender.sendMessage(I18n.translate("command.world.unimport.success", worldName));
            return;
        }

        sender.sendMessage(I18n.translate("command.world.unimport.failed", worldName, result.name()));
    }

    @SubCommand(path = "create")
    @CommandPermission(Permission.WORLD_CREATE)
    public void createWorld(Player sender, @IgnoreStrictEnum WorldType worldType) {
        if(worldType == null) {
            String validTypes = String.join(", ", WorldType.publicTypes().stream().map(Enum::name).toList());
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.type", validTypes));
            return;
        }

        if (worldType.isInternalType()) {
            String validTypes = String.join(", ", WorldType.publicTypes().stream().map(Enum::name).toList());
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.internal", worldType, validTypes));
            return;
        }

        worldManager.createWorld(sender, worldType);
    }

    @SubCommand(path = "create")
    @CommandPermission(Permission.WORLD_CREATE)
    public void createWorld(CommandSender sender, @IgnoreStrictEnum WorldType worldType, String worldName) {
        if(worldType == null) {
            String validTypes = String.join(", ", WorldType.publicTypes().stream().map(Enum::name).toList());
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.type", validTypes));
            return;
        }

        if (worldType.isInternalType()) {
            String validTypes = String.join(", ", WorldType.publicTypes().stream().map(Enum::name).toList());
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.internal", worldType, validTypes));
            return;
        }

        UUID creator = null;

        if (sender instanceof Player player) {
            creator = player.getUniqueId();
        }

        if (worldManager.createWorld(worldName, creator, worldType)) {
            sender.sendMessage(I18n.translate("world.creation.success"));
        } else {
            sender.sendMessage(I18n.translate("world.creation.failed"));
        }
    }

    @SubCommand(path = "tp")
    @CommandPermission(Permission.WORLD_TP)
    public void teleport(Player player, String worldName) {
        worldManager.getWorld(worldName)
                .ifPresentOrElse(world -> {
                    world.teleport(player, true);
                }, () -> {
                    player.sendMessage(I18n.translate("world.not_found.world_name", worldName));
                });
    }

    @FallbackCommand
    private void unknownCommand(Player player) {
        player.sendMessage(I18n.translate("command.unknown", "worlds", ""));
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

}
