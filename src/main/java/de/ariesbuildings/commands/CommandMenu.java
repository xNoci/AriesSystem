package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.*;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import me.noci.quickutilities.quickcommand.annotation.IgnoreStrictEnum;
import me.noci.quickutilities.utils.EnumUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMenu extends AriesCommand {

    public CommandMenu(JavaPlugin plugin) {
        super(plugin, "menu", "", "", "settings");
    }

    @Command
    private void onCommand(AriesPlayer player) {
        new MainMenuGui().provide(player);
    }

    @Command
    private void openMenu(AriesPlayer player, @IgnoreStrictEnum MenuType type) {
        if (type == null) {
            player.sendTranslate("command.menu.menu_parse_invalid.type", EnumUtils.join(", ", MenuType.class));
            return;
        }

        switch (type) {
            case CUSTOM_BLOCKS -> new CustomBlockGui().provide(player);
            case WORLD_LIST_PUBLIC -> new WorldListGui(WorldVisibility.PUBLIC).provide(player);
            case WORLD_LIST_PRIVATE -> new WorldListGui(WorldVisibility.PRIVATE).provide(player);
            case WORLD_LIST_ARCHIVED -> new WorldListGui(WorldVisibility.ARCHIVED).provide(player);
            case PLAYER_SETTINGS -> new PlayerSettingsGui().provide(player);
            case WORLD_SETTINGS -> {
                AriesSystem.getInstance().getWorldManager().getWorld(player)
                        .ifPresentOrElse(world -> {
                            new WorldSettingsGui(world).provide(player);
                        }, () -> player.sendTranslate("world.not_found.current_world"));

            }
        }
    }

    @FallbackCommand
    private void notForConsole(CommandSender sender) {
        sender.sendMessage(I18n.translate("command.only_for_player"));
    }

    private enum MenuType {
        PLAYER_SETTINGS,
        WORLD_SETTINGS,
        WORLD_LIST_PUBLIC,
        WORLD_LIST_PRIVATE,
        WORLD_LIST_ARCHIVED,
        CUSTOM_BLOCKS
    }

}
