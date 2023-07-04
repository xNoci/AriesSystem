package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.WorldImportResult;
import de.ariesbuildings.world.WorldType;
import me.noci.quickutilities.quickcommand.annotations.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;

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

    @Subcommand("create")
    @CommandPermission(Permission.WORLD_CREATE)
    @CommandArgs(1)
    public void createWorld(Player player, String[] args) {
        String rawWorldType = args[1];
        parseWorldType(player, rawWorldType)
                .ifPresent(worldType -> {
                    worldManager.createWorld(player, worldType);
                });
    }

    @Subcommand("create")
    @CommandPermission(Permission.WORLD_CREATE)
    @CommandArgs(2)
    public void createWorld(CommandSender sender, String[] args) {
        String rawWorldType = args[1];
        String worldName = args[2];
        parseWorldType(sender, rawWorldType)
                .ifPresent(worldType -> {
                    UUID creator = null;

                    if (sender instanceof Player player) {
                        creator = player.getUniqueId();
                    }

                    if (worldManager.createWorld(worldName, creator, worldType)) {
                        sender.sendMessage(I18n.translate("world.creation.success"));
                    } else {
                        sender.sendMessage(I18n.translate("world.creation.failed"));
                    }
                });
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

    private Optional<WorldType> parseWorldType(CommandSender sender, String type) {
        WorldType worldType;
        try {
            worldType = WorldType.valueOf(type);
        } catch (Exception e) {
            String validTypes = String.join(", ", WorldType.publicTypes().stream().map(Enum::name).toList());
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.type", type, validTypes));
            return Optional.empty();
        }

        if (worldType.isInternalType()) {
            String validTypes = String.join(", ", WorldType.publicTypes().stream().map(Enum::name).toList());
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.internal", type, validTypes));
            return Optional.empty();
        }

        return Optional.of(worldType);
    }


}
