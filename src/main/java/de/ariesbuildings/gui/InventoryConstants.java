package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import com.google.common.base.CaseFormat;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.utils.DateUtils;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldStatus;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryConstants {

    public static final QuickItemStack ITM_BACKGROUND_BLACK = new QuickItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), "§8");
    public static final QuickItemStack PREVIOUS_PAGE = new QuickItemStack(XMaterial.ARROW.parseMaterial(), I18n.translate("gui.constants.item_name.previous_page")).addItemFlags();
    public static final QuickItemStack NEXT_PAGE = new QuickItemStack(XMaterial.ARROW.parseMaterial(), I18n.translate("gui.constants.item_name.next_page")).addItemFlags();
    public static final QuickItemStack PREVIOUS_GUI = new QuickItemStack(XMaterial.FEATHER.parseMaterial(), I18n.translate("gui.constants.item_name.previous_gui")).addItemFlags();
    public static final ItemStack INVISIBLE_POTION = XPotion.buildItemWithEffects(XMaterial.POTION.parseMaterial(), XPotion.INVISIBILITY.getPotionEffectType().getColor());

    public static final GuiItem BACKGROUND_BLACK = ITM_BACKGROUND_BLACK.asGuiItem();

    public static final int FULL_INV_SIZE = 9 * 6;

    public static GuiItem openPreviousGui(QuickGUIProvider gui) {
        return PREVIOUS_GUI.asGuiItem(event -> gui.provide(event.getPlayer()));
    }

    public static QuickItemStack worldDisplayIcon(AriesWorld world) {
        Material displayIcon = world.getDisplayIcon().or(XMaterial.GRASS_BLOCK).parseMaterial();
        String displayName = I18n.translate("gui.world_list.item.world_display.displayname", world.getWorldName());
        QuickItemStack worldItem = new QuickItemStack(displayIcon, displayName);
        worldItem.addItemFlags();

        String typeLore = I18n.translate("gui.world_list.item.world_display.lore.type", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, world.getType().name()));
        String visibilityLore = I18n.translate("gui.world_list.item.world_display.lore.visibility", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, world.getVisibility().name()));
        String statusLore = I18n.translate("gui.world_list.item.world_display.lore.status", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, world.getOptions().get(WorldOption.WORLD_STATUS, WorldStatus.class).name()));
        String creationTimeLore = I18n.translate("gui.world_list.item.world_display.lore.creationTime", DateUtils.asDate(world.getCreationTime()));
        String creatorLore = I18n.translate("gui.world_list.item.world_display.lore.creator", world.getCreatorAsString());

        worldItem.setLore("", typeLore, visibilityLore, statusLore, creationTimeLore, creatorLore);

        return worldItem;
    }

}
