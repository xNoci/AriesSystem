package de.ariesbuildings.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUI {

    @Getter private final String name;
    private final int rows;
    private final Inventory inv;

    public GUI(String name, int rows) {
        this.name = name;
        this.rows = rows;
        inv = Bukkit.createInventory(null, rows * 9, name);
    }

    public void open(Player player) {
        player.openInventory(inv);
    }

    public GUI addItem(ItemStack is) {
        inv.addItem(is);
        return this;
    }

    public GUI addItem(ClickableItem ci) {
        inv.addItem(ci.getIs());
        return this;
    }

    public GUI addItemFromTo(ItemStack is, int fromSlot, int toSlot) {
        for (int i = fromSlot; i <= toSlot; i++) {
            inv.setItem(i, is);
        }
        return this;
    }

    public int getSize() {
        return rows * 9;
    }
}
