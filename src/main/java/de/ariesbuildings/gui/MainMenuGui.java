package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.gui.guiitem.GuiItemButton;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.provider.AriesGuiProvider;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.inventory.SlotClickEvent;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitRunnable;

public class MainMenuGui extends AriesGuiProvider {

    private static final int WHITELIST_TOGGLE_SLOT = Slot.getSlot(6, 2);

    private final GuiItem publicWorlds;
    private final GuiItem privateWorlds;
    private final GuiItem archivedWorlds;
    private final GuiItem customBlockMenu;
    private final GuiItem playerSettings;
    private final GuiItem stopServer;

    public MainMenuGui() {
        super(I18n.translate("gui.main_menu.title"), InventoryConstants.FULL_INV_SIZE);

        //TODO add descriptions
        this.publicWorlds = GuiItemButton.builder(XMaterial.WRITABLE_BOOK, "gui.main_menu.item.worlds_public.displayname")
                .event(event -> openWorldList(event, WorldVisibility.PUBLIC)).build();
        this.privateWorlds = GuiItemButton.builder(XMaterial.BOOK, "gui.main_menu.item.worlds_private.displayname")
                .event(event -> openWorldList(event, WorldVisibility.PRIVATE)).build();
        this.archivedWorlds = GuiItemButton.builder(XMaterial.BOOKSHELF, "gui.main_menu.item.worlds_archive.displayname")
                .event(event -> openWorldList(event, WorldVisibility.ARCHIVED)).build();
        this.customBlockMenu = GuiItemButton.builder(XMaterial.CHEST, "gui.main_menu.item.custom_block_menu.displayname")
                .provider(new CustomBlockGui(this)).build();
        this.playerSettings = GuiItemButton.builder(XMaterial.COMPARATOR, "gui.main_menu.item.player_settings.displayname")
                .provider(new PlayerSettingsGui(this)).build();
        this.stopServer = GuiItemButton.builder(XMaterial.LEVER, "gui.main_menu.item.stop_server.displayname")
                .lore(I18n.translate("gui.main_menu.item.lore.shift_left"))
                .glow()
                .event(MainMenuGui::onClickStopServer)
                .build();
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.setItem(Slot.getSlot(2, 3), publicWorlds);
        content.setItem(Slot.getSlot(2, 4), privateWorlds);
        content.setItem(Slot.getSlot(2, 5), archivedWorlds);

        content.setItem(Slot.getSlot(4, 3), customBlockMenu);
        content.setItem(Slot.getSlot(4, 7), playerSettings);

        if (player.hasPermission(Permission.OWNR_GUI_STOP_SERVER)) {
            content.setItem(Slot.getSlot(6, 1), stopServer);
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
        QuickItemStack whitelist = new QuickItemStack(XMaterial.PAPER.parseMaterial())
                .setDisplayName(I18n.translate("gui.main_menu.item.whitelist_toggle.displayname"))
                .glow()
                .addItemFlags();
        String whitelistStatus = Bukkit.hasWhitelist() ? I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_enabled") : I18n.translate("gui.main_menu.item.whitelist_toggle.lore.wl_disabled");
        whitelist.setLore("", I18n.translate("gui.main_menu.item.lore.shift_left"), whitelistStatus);
        return whitelist.asGuiItem(event -> {
            if (event.getClick() != ClickType.SHIFT_LEFT) return;
            Bukkit.setWhitelist(!Bukkit.hasWhitelist());
            content.setItem(WHITELIST_TOGGLE_SLOT, whitlistItem(content));
            content.applyContent();
        });
    }


}
