package de.ariesbuildings.gui;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.CustomBlockItem;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.provider.AriesPagedGuiProvider;
import de.ariesbuildings.gui.provider.AriesProvider;
import de.ariesbuildings.utils.CustomBlock;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.PageContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.EnumUtils;
import me.noci.quickutilities.utils.InventoryPattern;

import java.util.Arrays;

public class CustomBlockGui extends AriesPagedGuiProvider {

    private final AriesProvider previousGui;

    public CustomBlockGui() {
        this(null);
    }

    public CustomBlockGui(AriesProvider previousGui) {
        super(I18n.translate("gui.custom_blocks.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(2, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
    }

    @Override
    protected void initPage(AriesPlayer player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(2, 4));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        GuiItem[] items = EnumUtils.asStream(CustomBlock.class).map(CustomBlockItem::new).toArray(GuiItem[]::new);
        content.setPageContent(items);
        content.updatePage();
    }
}
