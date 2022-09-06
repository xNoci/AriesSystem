package de.ariesbuildings.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickableItemListener implements Listener {
    @EventHandler
    public void onClickableItemClick(InventoryClickEvent event) {
        ClickableItem item = ClickableItem.checkItem(event.getCurrentItem());
        if (item == null) return;
        item.getUsage().accept(item);
        event.setCancelled(true);
    }
}
