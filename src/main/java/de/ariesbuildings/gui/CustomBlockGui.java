package de.ariesbuildings.gui;

import de.ariesbuildings.I18n;
import de.ariesbuildings.utils.CustomBlock;
import me.noci.quickutilities.inventory.*;
import me.noci.quickutilities.utils.InventoryPattern;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CustomBlockGui extends PagedQuickGUIProvider {

    private final QuickGUIProvider previousGui;

    public CustomBlockGui() {
        this(null);
    }

    public CustomBlockGui(QuickGUIProvider previousGui) {
        super(I18n.translate("gui.custom_blocks.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(2, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
    }

    @Override
    public void initPage(Player player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(2, 4));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        GuiItem[] items = Arrays.stream(CustomBlock.values()).map(CustomBlockItem::new).toArray(GuiItem[]::new);
        content.setPageContent(items);
        content.updatePage();
    }
}
