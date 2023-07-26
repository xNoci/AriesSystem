package de.ariesbuildings.gui.guiitem.button;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.QuickItemStack;

public class GuiItemButton extends GuiItem {

    public static GuiItemButtonBuilder builder(XMaterial material, String displayName) {
        return new GuiItemButtonBuilder(material, displayName);
    }

    @Getter private final QuickItemStack itemStack;
    private final InventoryContent content;
    private final int slot;

    protected GuiItemButton(QuickItemStack itemStack, InventoryContent content, int slot) {
        this.itemStack = itemStack;
        this.content = content;
        this.slot = slot;
    }

    public void update() {
        content.setItem(slot, this);
        content.applyContent();
    }

}
