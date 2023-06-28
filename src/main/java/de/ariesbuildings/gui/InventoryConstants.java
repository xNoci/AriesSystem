package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.base.CaseFormat;
import de.ariesbuildings.I18n;
import de.ariesbuildings.world.AriesWorld;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryConstants {

    public static final ItemStack ITM_BACKGROUND_BLACK = new QuickItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), "§8");
    public static final ItemStack PREVIOUS_PAGE = new QuickItemStack(XMaterial.ARROW.parseMaterial(), I18n.translate("gui.constants.item_name.previous_page")).addItemFlags();
    public static final ItemStack NEXT_PAGE = new QuickItemStack(XMaterial.ARROW.parseMaterial(), I18n.translate("gui.constants.item_name.next_page")).addItemFlags();
    public static final ItemStack PREVIOUS_GUI = new QuickItemStack(XMaterial.FEATHER.parseMaterial(), I18n.translate("gui.constants.item_name.previous_gui")).addItemFlags();

    public static final GuiItem BACKGROUND_BLACK = GuiItem.of(ITM_BACKGROUND_BLACK);

    public static final int FULL_INV_SIZE = 9 * 6;

    public static GuiItem openPreviousGui(QuickGUIProvider gui) {
        return GuiItem.of(PREVIOUS_GUI, event -> gui.provide(event.getPlayer()));
    }

    public static QuickItemStack worldDisplayIcon(AriesWorld world) {
        Material displayIcon = world.getDisplayIcon().or(XMaterial.GRASS_BLOCK).parseMaterial();
        String displayName = I18n.translate("gui.world_list.item.world_display.displayname", world.getWorldName());
        QuickItemStack worldItem = new QuickItemStack(displayIcon, displayName);
        worldItem.addItemFlags();

        String typeLore = I18n.translate("gui.world_list.item.world_display.lore.type", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, world.getType().name()));
        String visibilityLore = I18n.translate("gui.world_list.item.world_display.lore.visibility", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, world.getVisibility().name()));
        String creationTimeLore = I18n.translate("gui.world_list.item.world_display.lore.creationTime", world.getCreationTime()); //Todo tp readable format
        String creatorLore = I18n.translate("gui.world_list.item.world_display.lore.creator", world.getCreatorAsString());

        worldItem.setLore("", typeLore, visibilityLore, creationTimeLore, creatorLore);

        return worldItem;
    }

}
