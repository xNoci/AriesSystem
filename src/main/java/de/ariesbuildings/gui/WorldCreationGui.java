package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.guiitem.button.GuiItemButton;
import de.ariesbuildings.gui.provider.AriesGuiProvider;
import de.ariesbuildings.gui.provider.AriesProvider;
import de.ariesbuildings.gui.provider.DisplayIconChanger;
import de.ariesbuildings.utils.Input;
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.Require;
import me.noci.quickutilities.utils.SkullItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class WorldCreationGui extends AriesGuiProvider implements DisplayIconChanger {

    private static final QuickItemStack CHECK_MARK = SkullItem.getSkull("a79a5c95ee17abfef45c8dc224189964944d560f19a44f19f8a46aef3fee4756");
    private static final QuickItemStack CROSS_MARK = SkullItem.getSkull("27548362a24c0fa8453e4d93e68c5969ddbde57bf6666c0319c1ed1e84d89065");

    private final AriesProvider previousGui;

    private WorldVisibility currentVisibility = WorldVisibility.PUBLIC;
    private WorldType currentType = WorldType.VOID;
    private XMaterial currentIcon = XMaterial.GRASS_BLOCK;
    private String currentWorldName = null;

    private GuiItemButton selectPublic;
    private GuiItemButton selectPrivate;
    private GuiItemButton selectArchived;

    private GuiItemButton selectNormal;
    private GuiItemButton selectFlat;
    private GuiItemButton selectVoid;

    public WorldCreationGui() {
        this(null);
    }

    public WorldCreationGui(AriesProvider previousGui) {
        super(I18n.translate("gui.world_creation.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));

        this.selectPublic = visibilityButton(Slot.getSlot(4, 2), XMaterial.WRITABLE_BOOK, WorldVisibility.PUBLIC, content);
        this.selectPrivate = visibilityButton(Slot.getSlot(4, 3), XMaterial.BOOK, WorldVisibility.PRIVATE, content);
        this.selectArchived = visibilityButton(Slot.getSlot(4, 4), XMaterial.BOOKSHELF, WorldVisibility.ARCHIVED, content);

        this.selectNormal = typeButton(Slot.getSlot(4, 6), XMaterial.DIRT, WorldType.NORMAL, content);
        this.selectFlat = typeButton(Slot.getSlot(4, 7), XMaterial.GRASS_BLOCK, WorldType.FLAT, content);
        this.selectVoid = typeButton(Slot.getSlot(4, 8), XMaterial.BLACK_WOOL, WorldType.VOID, content);

        GuiItemButton.builder(currentIcon.or(XMaterial.GRASS_BLOCK), "gui.world_creation.display_icon.displayname")
                .lore("", I18n.translate("gui.world_creation.display_icon.description"))
                .clickType(ClickType.LEFT)
                .provider(new DisplayIconChooseGui(this))
                .build(content, Slot.getSlot(2, 4));

        var signBuilder = GuiItemButton.builder(isNameValid() ? XMaterial.BIRCH_SIGN : XMaterial.DARK_OAK_SIGN, "gui.world_creation.change_name.displayname")
                .clickType(ClickType.LEFT)
                .click((eventPlayer, itemButton) -> {
                    List<String> signLines = Lists.newArrayList();
                    signLines.add(I18n.translate("gui.world_creation.change_name.input.line.2"));
                    signLines.add(I18n.translate("gui.world_creation.change_name.input.line.3"));
                    signLines.add(I18n.translate("gui.world_creation.change_name.input.line.4"));

                    Input.sign(eventPlayer, 1, signLines, input -> {
                        this.currentWorldName = input;
                        this.provide(eventPlayer, 1, BukkitUnit.TICKS);
                    }, inputPlayer -> {
                        this.currentWorldName = null;
                        this.provide(inputPlayer, 1, BukkitUnit.TICKS);
                    });
                });
        if(isNameValid()) {
            signBuilder.lore("", I18n.translate("gui.world_creation.change_name.lore.valid", currentWorldName), I18n.translate("gui.world_creation.change_name.lore.left_click_to_change"));
        } else {
            signBuilder.lore("", I18n.translate("gui.world_creation.change_name.lore.left_click_to_change"));
        }
        signBuilder.build(content, Slot.getSlot(2, 6));

        GuiItem finishItem = getFinishItem();
        content.setItem(Slot.getSlot(6, 8), finishItem);

        update(player.getBase(), content);
    }

    @Override
    public void update(Player player, InventoryContent content) {
        selectPublic.getItemStack().glow(currentVisibility != WorldVisibility.PUBLIC);
        selectPrivate.getItemStack().glow(currentVisibility != WorldVisibility.PRIVATE);
        selectArchived.getItemStack().glow(currentVisibility != WorldVisibility.ARCHIVED);

        selectNormal.getItemStack().glow(currentType != WorldType.NORMAL);
        selectFlat.getItemStack().glow(currentType != WorldType.FLAT);
        selectVoid.getItemStack().glow(currentType != WorldType.VOID);
    }

    private GuiItem getFinishItem() {
        if (isNameValid()) {
            return ((QuickItemStack) CHECK_MARK.clone())
                    .setDisplayName(I18n.translate("gui.world_creation.finish.displayname"))
                    .setLore(I18n.translate("gui.world_creation.finish.description"))
                    .addItemFlags()
                    .asGuiItem(event -> {
                        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());
                        if (event.getClick() != ClickType.LEFT) return;
                        player.getBase().closeInventory();
                        var worldManager = AriesSystem.getInstance().getWorldManager();
                        if (worldManager.createWorld(currentWorldName, player.getUUID(), currentType, currentVisibility, currentIcon)) {
                            player.sendTranslate("world.creation.success");
                        } else {
                            player.sendTranslate("world.creation.failed");
                        }
                    });
        }

        return ((QuickItemStack) CROSS_MARK.clone())
                .setDisplayName(I18n.translate("gui.world_creation.cant_finish.displayname"))
                .setLore(I18n.translate("gui.world_creation.cant_finish.description"))
                .addItemFlags()
                .asGuiItem();
    }

    private boolean isNameValid() {
        if (Require.isBlank(currentWorldName)) return false;
        return !AriesSystem.getInstance().getWorldManager().existsWorld(currentWorldName);
    }

    private GuiItemButton visibilityButton(int slot, XMaterial material, WorldVisibility visibility, InventoryContent content) {
        return GuiItemButton.builder(material, "gui.world_creation.select_visibility_%s.displayname".formatted(visibility.name().toLowerCase()))
                .clickType(ClickType.LEFT)
                .click((player, itemButton) -> {
                    currentVisibility = visibility;
                    update(player.getBase(), content);
                    content.applyContent();
                }).build(content, slot);
    }

    private GuiItemButton typeButton(int slot, XMaterial material, WorldType type, InventoryContent content) {
        return GuiItemButton.builder(material, "gui.world_creation.select_type_%s.displayname".formatted(type.name().toLowerCase()))
                .clickType(ClickType.LEFT)
                .click((player, itemButton) -> {
                    currentType = type;
                    update(player.getBase(), content);
                    content.applyContent();
                }).build(content, slot);
    }

    @Override
    public void onIconSelect(XMaterial icon) {
        this.currentIcon = icon;
    }
}
