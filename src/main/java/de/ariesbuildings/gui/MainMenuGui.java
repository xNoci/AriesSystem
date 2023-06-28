package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.*;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitRunnable;

public class MainMenuGui extends QuickGUIProvider {

    //ITEMS
    private static final QuickItemStack ITEM_PLAYER_SETTINGS = new QuickItemStack(XMaterial.COMPARATOR.parseMaterial(), I18n.translate("gui.main_menu.item.player_settings.displayname")).addItemFlags();
    private static final QuickItemStack ITEM_WORLDS_PUBLIC = new QuickItemStack(XMaterial.WRITABLE_BOOK.parseMaterial(), I18n.translate("gui.main_menu.item.worlds_public.displayname")).addItemFlags();
    private static final QuickItemStack ITEM_WORLDS_PRIVATE = new QuickItemStack(XMaterial.BOOK.parseMaterial(), I18n.translate("gui.main_menu.item.worlds_private.displayname")).addItemFlags();
    private static final QuickItemStack ITEM_WORLDS_ARCHIVE = new QuickItemStack(XMaterial.BOOKSHELF.parseMaterial(), I18n.translate("gui.main_menu.item.worlds_archive.displayname")).addItemFlags();

    private static final QuickItemStack ITEM_CUSTOM_BLOCK_MENU = new QuickItemStack(XMaterial.CHEST.parseMaterial(), I18n.translate("gui.main_menu.item.custom_block_menu.displayname")).addItemFlags();

    private static final QuickItemStack ITEM_OWNR_STOP_SERVER = new QuickItemStack(XMaterial.LEVER.parseMaterial(), I18n.translate("gui.main_menu.item.stop_server.displayname")).setLore(I18n.translate("gui.main_menu.item.lore.shift_left")).glow().addItemFlags();
    private static final QuickItemStack ITEM_OWNR_TOGGLE_WHITELIST = new QuickItemStack(XMaterial.PAPER.parseMaterial(), I18n.translate("gui.main_menu.item.whitelist_toggle.displayname")).glow().addItemFlags();

    //GUI ITEMS
    private static final GuiItem PLAYER_SETTINGS = GuiItem.of(ITEM_PLAYER_SETTINGS, event -> new PlayerSettingsGui(new MainMenuGui()).provide(event.getPlayer()));
    private static final GuiItem PUBLIC_WORLDS = GuiItem.of(ITEM_WORLDS_PUBLIC, event -> openWorldList(event, WorldVisibility.PUBLIC));
    private static final GuiItem PRIVATE_WORLDS = GuiItem.of(ITEM_WORLDS_PRIVATE, event -> openWorldList(event, WorldVisibility.PRIVATE));
    private static final GuiItem ARCHIVED_WORLDS = GuiItem.of(ITEM_WORLDS_ARCHIVE, event -> openWorldList(event, WorldVisibility.ARCHIVED));

    private static final GuiItem CUSTOM_BLOCK_MENU = GuiItem.of(ITEM_CUSTOM_BLOCK_MENU, event -> new CustomBlockGui(new MainMenuGui()).provide(event.getPlayer()));

    private static final GuiItem OWNR_STOP_SERVER = GuiItem.of(ITEM_OWNR_STOP_SERVER, MainMenuGui::onClickStopServer);

    private static final int WHITELIST_TOGGLE_SLOT = Slot.getSlot(6, 2);

    public MainMenuGui() {
        super(I18n.translate("gui.main_menu.title"), InventoryConstants.FULL_INV_SIZE);
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.setItem(Slot.getSlot(2, 3), PUBLIC_WORLDS);
        content.setItem(Slot.getSlot(2, 4), PRIVATE_WORLDS);
        content.setItem(Slot.getSlot(2, 7), ARCHIVED_WORLDS);

        content.setItem(Slot.getSlot(4, 3), CUSTOM_BLOCK_MENU);
        content.setItem(Slot.getSlot(4, 7), PLAYER_SETTINGS);

        if (player.hasPermission(Permission.OWNR_GUI_STOP_SERVER)) {
            content.setItem(Slot.getSlot(6, 1), OWNR_STOP_SERVER);
            content.setItem(WHITELIST_TOGGLE_SLOT, whitlistItem(content));
        }

    }

    private static void openWorldList(SlotClickEvent event, WorldVisibility visibility) {
        Player player = event.getPlayer();
        new WorldListGui(visibility, new MainMenuGui()).provide(player);
    }

    private static void onClickStopServer(SlotClickEvent event) {
        ClickType clickType = event.getClick();
        if (clickType != ClickType.SHIFT_LEFT) return;
        Bukkit.broadcastMessage(I18n.translate("broadcast.notify.server_shutdown", AriesSystemConfig.SERVER_SHUTDOW_DELAY));
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(AriesSystem.getInstance(), BukkitUnit.SECONDS.toTicks(AriesSystemConfig.SERVER_SHUTDOW_DELAY));
    }

    private GuiItem whitlistItem(InventoryContent content) {
        QuickItemStack whitelist = ITEM_OWNR_TOGGLE_WHITELIST;
        String whitelistStatus = Bukkit.hasWhitelist() ? I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_enabled") : I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_disabled");
        whitelist.setLore("", I18n.translate("gui.main_menu.item.lore.shift_left"), whitelistStatus);
        return GuiItem.of(whitelist, event -> {
            if (event.getClick() != ClickType.SHIFT_LEFT) return;
            Bukkit.setWhitelist(!Bukkit.hasWhitelist());
            content.setItem(WHITELIST_TOGGLE_SLOT, whitlistItem(content));
            content.applyContent();
        });
    }


}
