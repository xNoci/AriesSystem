package de.ariesbuildings.gui.guiitem.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.Require;

import java.util.HashMap;

public class OptionItemBuilder<O extends Option, V> {

    public static <O extends Option, V> OptionItemBuilder<O, V> of(O option, Class<V> valueType) {
        return new OptionItemBuilder<>(option, valueType);
    }

    protected final HashMap<V, QuickItemStack> valueMap = Maps.newHashMap();
    protected final O option;
    protected final Class<V> valueType;
    private V currentValue;
    protected InventoryContent content;
    protected OptionHolder<O> optionHolder;
    protected int slot = -1;
    protected ClickCondition clickCondition;

    //Integer option
    protected QuickItemStack integerItem = null;
    protected int lowerBound = -1;
    protected int upperBound = -1;
    protected int increment = -1;

    private OptionItemBuilder(O option, Class<V> valueType) {
        this.option = option;
        this.valueType = valueType;
    }

    public OptionItemBuilder<O, V> currentValue(V currentValue) {
        Require.checkState(this.currentValue == null, "Cannot set currentValue twice.");
        this.currentValue = currentValue;
        return this;
    }

    public OptionItemBuilder<O, V> inventoryContent(InventoryContent inventoryContent) {
        Require.checkState(this.content == null, "Cannot set content twice.");
        this.content = inventoryContent;
        return this;
    }

    public OptionItemBuilder<O, V> optionHolder(OptionHolder<O> holder) {
        Require.checkState(this.optionHolder == null, "Cannot set optionHolder twice.");
        this.optionHolder = holder;
        return this;
    }

    public OptionItemBuilder<O, V> slot(int row, int column) {
        Require.checkState(this.slot == -1, "Cannot set slot twice.");
        this.slot = Slot.getSlot(row, column);
        return this;
    }

    public OptionItemBuilder<O, V> clickCondition(ClickCondition clickCondition) {
        Require.checkState(this.clickCondition == null, "Cannot set clickCondition twice.");
        this.clickCondition = clickCondition;
        return this;
    }

    public OptionItemBuilder<O, V> mapValue(V value, QuickItemStack itemStack) {
        Require.checkState(!valueMap.containsKey(value), "There is already a mapping for value '%s'.".formatted(value));
        valueMap.put(value, itemStack);
        return this;
    }

    public OptionItemBuilder<O, V> integerItem(QuickItemStack item) {
        Require.checkState(integerItem == null, "Cannot set integerItem twice");
        this.integerItem = item;
        return this;
    }

    public OptionItemBuilder<O, V> lowerBound(int lowerBound) {
        Require.checkState(this.lowerBound == -1, "Cannot set lowerBound twice.");
        Require.checkArgument(lowerBound >= 0, "lowerBound must be greater than or equal to zero.");
        this.lowerBound = lowerBound;
        return this;
    }

    public OptionItemBuilder<O, V> upperBound(int upperBound) {
        Require.checkState(this.upperBound == -1, "Cannot set upperBound twice.");
        Require.checkArgument(upperBound > 0, "upperBound must be greater than zero.");
        this.upperBound = upperBound;
        return this;
    }

    public OptionItemBuilder<O, V> increment(int increment) {
        Require.checkState(this.increment == -1, "Cannot set increment twice.");
        Require.checkArgument(increment > 0, "increment must be greater than zero.");
        this.increment = increment;
        return this;
    }

    public void build() {
        V value = currentValue();
        OptionItemFactory.build(value.getClass(), this);
    }

    protected V currentValue() {
        if (currentValue != null) return currentValue;
        return optionHolder.get(option, valueType);
    }

}
