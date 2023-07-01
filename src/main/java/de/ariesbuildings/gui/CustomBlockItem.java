package de.ariesbuildings.gui;

import de.ariesbuildings.utils.CustomBlock;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.SlotClickEvent;
import org.bukkit.event.inventory.ClickType;

public class CustomBlockItem extends GuiItem {


    private final CustomBlock customBlock;


    protected CustomBlockItem(CustomBlock customBlock) {
        this.customBlock = customBlock;

        setAction(this::slotClickEvent);
        setItem(customBlock.getDisplayItem());
    }

    private void slotClickEvent(SlotClickEvent event) {
        if (event.getClick() != ClickType.LEFT) return;

        event.getPlayer().getInventory().addItem(customBlock.getDisplayItem());
    }

}
