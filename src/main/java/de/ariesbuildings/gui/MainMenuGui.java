package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.guiitem.button.GuiItemButton;
import de.ariesbuildings.gui.provider.AriesGuiProvider;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;

public class MainMenuGui extends AriesGuiProvider {

    public MainMenuGui() {
        super(I18n.translate("gui.main_menu.title"), InventoryConstants.FULL_INV_SIZE);
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);

        //TODO add descriptions
        GuiItemButton.builder(XMaterial.WRITABLE_BOOK, "gui.main_menu.item.worlds_public.displayname")
                .click((ariesPlayer, button) -> openWorldList(ariesPlayer, WorldVisibility.PUBLIC))
                .build(content, Slot.getSlot(2, 3));
        GuiItemButton.builder(XMaterial.BOOK, "gui.main_menu.item.worlds_private.displayname")
                .click((ariesPlayer, button) -> openWorldList(ariesPlayer, WorldVisibility.PRIVATE))
                .build(content, Slot.getSlot(2, 4));
        GuiItemButton.builder(XMaterial.BOOKSHELF, "gui.main_menu.item.worlds_archive.displayname")
                .click((ariesPlayer, button) -> openWorldList(ariesPlayer, WorldVisibility.ARCHIVED))
                .build(content, Slot.getSlot(2, 5));
        GuiItemButton.builder(XMaterial.ANVIL, "gui.main_menu.item.world_creation.displayname")
                .provider(new WorldCreationGui(this))
                .build(content, Slot.getSlot(2, 7));
        GuiItemButton.builder(XMaterial.CHEST, "gui.main_menu.item.custom_block_menu.displayname")
                .provider(new CustomBlockGui(this))
                .build(content, Slot.getSlot(4, 3));
        GuiItemButton.builder(XMaterial.COMPARATOR, "gui.main_menu.item.player_settings.displayname")
                .provider(new PlayerSettingsGui(this))
                .build(content, Slot.getSlot(4, 7));

        if (player.hasPermission(Permission.OWNR_GUI_STOP_SERVER)) {
            GuiItemButton.builder(XMaterial.LEVER, "gui.main_menu.item.stop_server.displayname")
                    .lore(I18n.translate("gui.main_menu.item.lore.shift_left"))
                    .glow()
                    .clickType(ClickType.SHIFT_LEFT)
                    .click(MainMenuGui::onClickStopServer)
                    .build(content, Slot.getSlot(6, 1));

            String whitelistStatus = Bukkit.hasWhitelist() ? I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_enabled") : I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_disabled");
            GuiItemButton.builder(XMaterial.PAPER, "gui.main_menu.item.whitelist_toggle.displayname")
                    .glow()
                    .lore("", I18n.translate("gui.main_menu.item.lore.shift_left"), whitelistStatus)
                    .clickType(ClickType.SHIFT_LEFT)
                    .click(MainMenuGui::onClickWhiteList)
                    .build(content, Slot.getSlot(6, 2));
        }
    }

    private void openWorldList(AriesPlayer player, WorldVisibility visibility) {
        new WorldListGui(visibility, this).provide(player);
    }

    private static void onClickWhiteList(AriesPlayer ariesPlayer, GuiItemButton itemButton) {
        Bukkit.setWhitelist(!Bukkit.hasWhitelist());
        QuickItemStack whiteListItem = itemButton.getItemStack();
        String whitelistStatus = Bukkit.hasWhitelist() ? I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_enabled") : I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_disabled");
        whiteListItem.setLore("", I18n.translate("gui.main_menu.item.lore.shift_left"), whitelistStatus);
        itemButton.update();
    }

    private static void onClickStopServer(AriesPlayer ariesPlayer, GuiItemButton itemButton) {
        Bukkit.broadcastMessage(I18n.translate("broadcast.notify.server_shutdown", AriesSystemConfig.SERVER_SHUTDOW_DELAY));
        Scheduler.delay(AriesSystemConfig.SERVER_SHUTDOW_DELAY, BukkitUnit.SECONDS, Bukkit::shutdown);
    }


}
