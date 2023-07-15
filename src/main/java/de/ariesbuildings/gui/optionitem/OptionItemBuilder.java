package de.ariesbuildings.gui.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;

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
        if (this.currentValue != null) throw new IllegalStateException("Cannot set currentValue twice.");
        this.currentValue = currentValue;
        return this;
    }

    public OptionItemBuilder<O, V> inventoryContent(InventoryContent inventoryContent) {
        if (this.content != null) throw new IllegalStateException("Cannot set content twice.");
        this.content = inventoryContent;
        return this;
    }

    public OptionItemBuilder<O, V> optionHolder(OptionHolder<O> holder) {
        if (this.optionHolder != null) throw new IllegalStateException("Cannot set optionHolder twice.");
        this.optionHolder = holder;
        return this;
    }

    public OptionItemBuilder<O, V> slot(int row, int column) {
        if (this.slot != -1) throw new IllegalStateException("Cannot set slot twice.");
        this.slot = Slot.getSlot(row, column);
        return this;
    }

    public OptionItemBuilder<O, V> clickCondition(ClickCondition clickCondition) {
        if (this.clickCondition != null) throw new IllegalStateException("Cannot set clickCondition twice.");
        this.clickCondition = clickCondition;
        return this;
    }

    public OptionItemBuilder<O, V> mapValue(V value, QuickItemStack itemStack) {
        if (valueMap.containsKey(value))
            throw new IllegalStateException("There is already a mapping for value '%s'.".formatted(value));
        valueMap.put(value, itemStack);
        return this;
    }

    public OptionItemBuilder<O, V> integerItem(QuickItemStack item) {
        if (integerItem != null) throw new IllegalStateException("Cannot set integerItem twice");
        this.integerItem = item;
        return this;
    }

    public OptionItemBuilder<O, V> lowerBound(int lowerBound) {
        if (this.lowerBound != -1) throw new IllegalStateException("Cannot set lowerBound twice.");
        if (lowerBound < 0) throw new IllegalArgumentException("lowerBound must be greater than or equal to zero.");
        this.lowerBound = lowerBound;
        return this;
    }

    public OptionItemBuilder<O, V> upperBound(int upperBound) {
        if (this.upperBound != -1) throw new IllegalStateException("Cannot set upperBound twice.");
        if (upperBound < 1) throw new IllegalArgumentException("upperBound must be greater than zero.");
        this.upperBound = upperBound;
        return this;
    }

    public OptionItemBuilder<O, V> increment(int increment) {
        if (this.increment != -1) throw new IllegalStateException("Cannot set increment twice.");
        if (increment < 1) throw new IllegalArgumentException("increment must be greater than zero.");
        this.increment = increment;
        return this;
    }

    public void build() {
        V value = currentValue();
        if(value instanceof Boolean) BooleanOptionItem.FACTORY.build((OptionItemBuilder<O, Boolean>) this);
        if(value instanceof Integer) IntegerOptionItem.FACTORY.build((OptionItemBuilder<O, Integer>)this);
        if(value instanceof Enum) EnumOptionItem.FACTORY.build(this);
    }

    protected V currentValue() {
        if (currentValue != null) return currentValue;
        return optionHolder.get(option, valueType);
    }

}
