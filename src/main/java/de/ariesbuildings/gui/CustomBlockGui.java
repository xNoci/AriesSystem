package de.ariesbuildings.gui;

import de.ariesbuildings.I18n;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import org.bukkit.entity.Player;

public class CustomBlockGui extends QuickGUIProvider {

    public CustomBlockGui() {
        super(I18n.translate("gui.custom_blocks.title"), 9 * 6);
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fillBorders(GuiItems.BACKGROUND_BLACK);
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
