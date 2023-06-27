package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.inventory.SlotClickEvent;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitRunnable;

public class MainMenuGUI extends QuickGUIProvider {

    //ITEMS
    private static final QuickItemStack ITEM_PLAYER_SETTINGS = new QuickItemStack(XMaterial.COMPARATOR.parseMaterial(), I18n.translate("gui.main_menu.item.player_settings")).addItemFlags();
    private static final QuickItemStack ITEM_WORLDS_PUBLIC = new QuickItemStack(XMaterial.WRITABLE_BOOK.parseMaterial(), I18n.translate("gui.main_menu.item.worlds_public")).addItemFlags();
    private static final QuickItemStack ITEM_WORLDS_PRIVATE = new QuickItemStack(XMaterial.BOOK.parseMaterial(), I18n.translate("gui.main_menu.item.worlds_private")).addItemFlags();
    private static final QuickItemStack ITEM_WORLDS_ARCHIVE = new QuickItemStack(XMaterial.BOOKSHELF.parseMaterial(), I18n.translate("gui.main_menu.item.worlds_archive")).addItemFlags();

    private static final QuickItemStack ITEM_CUSTOM_BLOCK_MENU = new QuickItemStack(XMaterial.CHEST.parseMaterial(), I18n.translate("gui.main_menu.item.custom_block_menu")).addItemFlags();

    private static final QuickItemStack ITEM_OWNR_STOP_SERVER = new QuickItemStack(XMaterial.LEVER.parseMaterial(), I18n.translate("gui.main_menu.item.stop_server")).setLore(I18n.translate("gui.main_menu.item.stop_server.lore")).glow().addItemFlags();

    //GUI ITEMS
    private static final GuiItem PLAYER_SETTINGS = GuiItem.of(ITEM_PLAYER_SETTINGS, MainMenuGUI::onClickPlayerSettings);
    private static final GuiItem PUBLIC_WORLDS = GuiItem.of(ITEM_WORLDS_PUBLIC, MainMenuGUI::onClickPlayerSettings);
    private static final GuiItem PRIVATE_WORLDS = GuiItem.of(ITEM_WORLDS_PRIVATE, MainMenuGUI::onClickPlayerSettings);
    private static final GuiItem ARCHIVED_WORLDS = GuiItem.of(ITEM_WORLDS_ARCHIVE, MainMenuGUI::onClickPlayerSettings);

    private static final GuiItem CUSTOM_BLOCK_MENU = GuiItem.of(ITEM_CUSTOM_BLOCK_MENU, MainMenuGUI::onCustomBlockMenu);

    private static final GuiItem OWNR_STOP_SERVER = GuiItem.of(ITEM_OWNR_STOP_SERVER, MainMenuGUI::onClickStopServer);

    public MainMenuGUI() {
        super(I18n.translate("gui.main_menu.title"), 9 * 6);
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(GuiItems.BACKGROUND_BLACK);
        content.setItem(9 + 2, PUBLIC_WORLDS);
        content.setItem(9 + 3, PRIVATE_WORLDS);
        content.setItem(9 + 6, ARCHIVED_WORLDS);

        content.setItem(9 * 5 + 8, PLAYER_SETTINGS);

        content.setItem(9 * 3 + 2, CUSTOM_BLOCK_MENU);

        if (player.hasPermission(Permission.OWNR_GUI_STOP_SERVER)) {
            content.setItem(9 * 5, OWNR_STOP_SERVER);
            //TODO Add toggle whitelist (show status in lore)
            // content.setItem(9 * 5 + 1, /*WHITELIST ITEM*/);
        }

    }

    private static void onClickPlayerSettings(SlotClickEvent event) {
        //TODO Open player settings menu
    }

    private static void onClickPublicWorlds(SlotClickEvent event) {
        //TODO Open public worlds menu
    }

    private static void onClickPrivateWorlds(SlotClickEvent event) {
        //TODO Open private worlds menu
    }

    private static void onClickArchivedWorlds(SlotClickEvent event) {
        //TODO Open archived worlds menu
    }

    private static void onCustomBlockMenu(SlotClickEvent event) {
        new CustomBlockGui().provide(event.getPlayer());
    }

    private static void onClickStopServer(SlotClickEvent event) {
        ClickType clickType = event.getClick();
        if (!clickType.isShiftClick() || !clickType.isLeftClick()) return;
        Bukkit.broadcastMessage(I18n.translate("broadcast.notify.server_shutdown", AriesSystemConfig.SERVER_SHUTDOW_DELAY));
        event.getPlayer().closeInventory();
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(AriesSystem.getInstance(), BukkitUnit.SECONDS.toTicks(AriesSystemConfig.SERVER_SHUTDOW_DELAY));
    }


}
