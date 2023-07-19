package de.ariesbuildings.commands;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.*;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.quickcommand.annotation.Command;
import me.noci.quickutilities.quickcommand.annotation.FallbackCommand;
import me.noci.quickutilities.quickcommand.annotation.IgnoreStrictEnum;
import me.noci.quickutilities.quickcommand.mappings.CommandMapping;
import me.noci.quickutilities.utils.EnumUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandMenu extends AriesCommand {

    static {
        CommandMapping.registerArgumentMapping(MenuType.class, MenuType::map);
    }

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

        PLAYER_SETTINGS("player"),
        WORLD_SETTINGS("world"),
        WORLD_LIST_PUBLIC("list_public", "public"),
        WORLD_LIST_PRIVATE("list_private", "private"),
        WORLD_LIST_ARCHIVED("list_archived", "archived"),
        CUSTOM_BLOCKS("blocks");

        private final List<String> alt;

        MenuType(String... alt) {
            this.alt = Lists.newArrayList(alt).stream().map(String::toLowerCase).toList();
        }

        public static MenuType map(String value) {
            for (MenuType menuType : values()) {
                if (menuType.name().equalsIgnoreCase(value) || menuType.alt.contains(value.toLowerCase())) return menuType;
            }
            return null;
        }

    }

}
