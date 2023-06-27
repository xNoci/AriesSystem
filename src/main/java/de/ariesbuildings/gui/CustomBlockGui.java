package de.ariesbuildings.gui;

import de.ariesbuildings.I18n;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.InventoryPattern;
import org.bukkit.entity.Player;

public class CustomBlockGui extends QuickGUIProvider {

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
        if(previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        // FURNACE WICH IS TURNED ON (AND THE OTHER FURNACE VARIANTS)
        // REDSONTE LAMP (TURNED ON)
        // DOUBLE STONE SLAB
        // HALF PISTON (STICKY/NON STICK) - Upper and lower part
        // MUSHROOM BLOCK VARIANTS
        // INVISIBLE ITEM FRAME
        // NETHER PORTAL
        // END PORTAL
    }
}
