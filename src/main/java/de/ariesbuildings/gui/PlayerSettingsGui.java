package de.ariesbuildings.gui;

import de.ariesbuildings.I18n;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.InventoryPattern;
import org.bukkit.entity.Player;

public class PlayerSettingsGui extends QuickGUIProvider {

    private final QuickGUIProvider previousGui;

    public PlayerSettingsGui() {
        this(null);
    }

    public PlayerSettingsGui(QuickGUIProvider previousGui) {
        super(I18n.translate("gui.player_settings.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        if(previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        //TODO
    }
}
