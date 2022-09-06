package de.ariesbuildings.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClickableItem {

    private static final List<ClickableItem> items = new ArrayList<>();
    @Getter private final String name;
    @Getter private final Material material;
    @Getter private final int stackSize;
    @Getter private final Consumer<ClickableItem> usage;
    @Getter private final List<String> lore;
    @Getter private final ItemStack itemStack;

    public ClickableItem(String name, Material material, int stackSize, Consumer<ClickableItem> usage, String... lore) {
        this.name = name;
        this.material = material;
        this.stackSize = stackSize;
        this.usage = usage;
        this.lore = List.of(lore);
        items.add(this);
        itemStack = new ItemStack(material, stackSize);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        im.setLore(this.lore);
        itemStack.setItemMeta(im);
    }

    public static ClickableItem checkItem(ItemStack is) {
        if (is == null) return null;
        for (ClickableItem item : items) {
            if (is.isSimilar(item.getItemStack())) return item;
        }
        return null;
    }
}