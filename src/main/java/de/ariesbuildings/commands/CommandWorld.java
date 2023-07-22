package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.RawLocation;
import de.ariesbuildings.world.WorldImportResult;
import de.ariesbuildings.world.WorldType;
import me.noci.quickutilities.quickcommand.annotation.*;
import me.noci.quickutilities.utils.EnumUtils;
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
    private void onUsage(AriesPlayer player) {
        displayCurrentWorld(player);
    }

    @SubCommand(path = "current")
    private void displayCurrentWorld(AriesPlayer player) {
        worldManager.getWorld(player)
                .ifPresentOrElse(world -> {
                    player.sendTranslate("command.world.current.display_name", world.getWorldName());
                }, () -> player.sendTranslate("command.world.current.not_imported"));
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
    public void createWorld(AriesPlayer sender, @IgnoreStrictEnum WorldType worldType) {
        if (worldType == null) {
            sender.sendTranslate("command.world.world_parse_invalid.type", EnumUtils.join(", ", WorldType.publicTypes()));
            return;
        }

        if (worldType.isInternalType()) {
            sender.sendTranslate("command.world.world_parse_invalid.internal", worldType, EnumUtils.join(", ", WorldType.publicTypes()));
            return;
        }

        worldManager.createWorld(sender, worldType);
    }

    @SubCommand(path = "create")
    @CommandPermission(Permission.WORLD_CREATE)
    public void createWorld(CommandSender sender, @IgnoreStrictEnum WorldType worldType, String worldName) {
        if (worldType == null) {
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.type", EnumUtils.join(", ", WorldType.publicTypes())));
            return;
        }

        if (worldType.isInternalType()) {
            sender.sendMessage(I18n.translate("command.world.world_parse_invalid.internal", worldType, EnumUtils.join(", ", WorldType.publicTypes())));
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

    @SubCommand(path = "setspawn")
    @CommandPermission(Permission.WORLD_SET_SPAWN)
    public void setSpawn(AriesPlayer player) {
        worldManager.getWorld(player)
                .ifPresentOrElse(world -> {
                    world.setWorldSpawn(new RawLocation(player.getLocation()));
                    player.sendTranslate("command.world.set_spawm.success");
                }, () -> player.sendTranslate("world.not_found.current_world"));
    }

    @SubCommand(path = "tp")
    @CommandPermission(Permission.WORLD_TP)
    public void teleport(AriesPlayer player, @MatchNull AriesWorld world) {
        if (world == null) {
            player.sendTranslate("world.not_found.world");
            return;
        }

        world.teleport(player, true);
    }

    @FallbackCommand
    private void unknownCommand(AriesPlayer player) {
        player.sendTranslate("command.unknown", "worlds", "");
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

}
