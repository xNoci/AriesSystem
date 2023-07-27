package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XTag;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.provider.AriesPagedGuiProvider;
import de.ariesbuildings.gui.provider.DisplayIconChanger;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.PageContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.EnumUtils;
import me.noci.quickutilities.utils.InventoryPattern;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.event.inventory.ClickType;

public class DisplayIconChooseGui extends AriesPagedGuiProvider {

    private final DisplayIconChanger iconChanger;

    public DisplayIconChooseGui(DisplayIconChanger iconChanger) {
        super(I18n.translate("gui.select_displayicon.title"), InventoryConstants.FULL_INV_SIZE);
        this.iconChanger = iconChanger;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(2, 5));
        content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(iconChanger));
    }

    @Override
    protected void initPage(AriesPlayer player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(2, 5));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        GuiItem[] icons = EnumUtils.asList(XMaterial.class).stream()
                .filter(material -> !XTag.INVENTORY_NOT_DISPLAYABLE.isTagged(material))
                .filter(material -> material.parseMaterial() != null)
                .map(this::toGuiItem)
                .toArray(GuiItem[]::new);
        content.setPageContent(icons);
    }

    private GuiItem toGuiItem(XMaterial material) {
        return new QuickItemStack(material.parseMaterial(), I18n.translate("gui.select_displayicon.icon.displayname", formatMaterialName(material)))
                .setLore("", I18n.translate("gui.select_displayicon.icon.description"))
                .addItemFlags()
                .asGuiItem(event -> {
                    if (event.getClick() != ClickType.LEFT) return;
                    AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());
                    iconChanger.onIconSelect(material);
                    iconChanger.provide(player);
                });
    }

    private String formatMaterialName(XMaterial material) {
        StringBuilder builder = new StringBuilder();
        for (String arg : material.name().split("_")) {
            builder.append(arg.substring(0, 1).toUpperCase());
            builder.append(arg.substring(1).toLowerCase());
            builder.append(" ");
        }
        return builder.toString();
    }

}
