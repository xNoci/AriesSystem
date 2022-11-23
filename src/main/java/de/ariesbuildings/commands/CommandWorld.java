package de.ariesbuildings.commands;

import de.ariesbuildings.I18n;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldImportResult;
import me.noci.quickutilities.quickcommand.annotations.CommandArgs;
import me.noci.quickutilities.quickcommand.annotations.DefaultCommand;
import me.noci.quickutilities.quickcommand.annotations.Subcommand;
import me.noci.quickutilities.quickcommand.annotations.UnknownCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
        AriesWorld world = worldManager.getWorld(player.getWorld());

        if (world == null) {
            player.sendMessage(I18n.translate("command.world.current.not_imported"));
            return;
        }

        player.sendMessage(I18n.translate("command.world.current.display_name", world.getWorldName()));
    }

    @Subcommand("physics")
    @CommandArgs(0)
    public void togglePhysics(Player player) {
        player.performCommand("antiblockupdate");
    }

    @Subcommand("import")
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

    @UnknownCommand
    private void unknownCommand(Player player) {
        player.sendMessage(I18n.translate("command.unknown", "worlds", ""));
    }


    @UnknownCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

}
