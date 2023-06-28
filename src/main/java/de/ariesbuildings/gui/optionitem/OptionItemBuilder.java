package de.ariesbuildings.gui.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;

import java.util.HashMap;

public class OptionItemBuilder<O extends Option, V> {

    public static <O extends Option, V> OptionItemBuilder<O, V> of(O option, V currentValue) {
        return new OptionItemBuilder<>(option, currentValue);
    }

    private final HashMap<V, QuickItemStack> valueMap = Maps.newHashMap();
    private final O option;
    private final V currentValue;
    private InventoryContent content;
    private OptionHolder<O> optionHolder;
    private int slot;
    private ClickCondition clickCondition;

    private OptionItemBuilder(O option, V currentValue) {
        this.option = option;
        this.currentValue = currentValue;
    }

    public OptionItemBuilder<O, V> inventoryContent(InventoryContent content) {
        this.content = content;
        return this;
    }

    public OptionItemBuilder<O, V> optionHolder(OptionHolder<O> holder) {
        this.optionHolder = holder;
        return this;
    }

    public OptionItemBuilder<O, V> slot(int row, int column) {
        this.slot = Slot.getSlot(row, column);
        return this;
    }

    public OptionItemBuilder<O, V> clickCondition(ClickCondition clickCondition) {
        if (this.clickCondition != null) throw new IllegalStateException("Cannot set clickCondition twice.");
        this.clickCondition = clickCondition;
        return this;
    }

    public OptionItemBuilder<O, V> mapValue(V value, QuickItemStack itemStack) {
        valueMap.put(value, itemStack);
        return this;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void build() {
        OptionItem item = null;

        if (currentValue instanceof Enum def) {
            item = new EnumOptionItem(def, optionHolder, option, content, slot);
        }

        if (currentValue instanceof Boolean def) {
            item = new BooleanOptionItem(def, optionHolder, option, content, slot);
        }

        if (item == null) {
            throw new IllegalStateException("Unexpected value: " + currentValue);
        }

        valueMap.forEach(item::mapValue);
    }

}
