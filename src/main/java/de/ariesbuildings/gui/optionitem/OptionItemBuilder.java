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

    private final HashMap<V, QuickItemStack> valueMap = Maps.newHashMap();
    private final O option;
    private final Class<V> valueType;
    private V currentValue;
    private InventoryContent content;
    private OptionHolder<O> optionHolder;
    private int slot = -1;
    private ClickCondition clickCondition;

    //Integer option
    private QuickItemStack integerItem = null;
    private int lowerBound = -1;
    private int upperBound = -1;
    private int increment = -1;

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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void build() {
        //TODO Validate - every thing required should be set
        OptionItem item = null;
        V value = currentValue();

        if (value instanceof Enum def) item = new EnumOptionItem(def, optionHolder, option, content, slot);
        if (value instanceof Boolean def) item = new BooleanOptionItem(def, optionHolder, option, content, slot);
        if (value instanceof Integer def) {
            item = new IntegerOptionItem(def, optionHolder, option, content, slot);

            if (lowerBound > upperBound) {
                int tmp = lowerBound;
                lowerBound = upperBound;
                upperBound = tmp;
            }

            if (lowerBound >= 0) ((IntegerOptionItem) item).setLowerBound(lowerBound);
            if (upperBound >= 0) ((IntegerOptionItem) item).setUpperBound(upperBound);
            if (increment > 0) ((IntegerOptionItem) item).setIncrement(increment);
            if (integerItem != null) ((IntegerOptionItem) item).setIntegerItem(integerItem);
        }

        if (item == null) throw new IllegalStateException("Unexpected value: " + currentValue);

        item.setClickCondition(clickCondition);

        if (!(value instanceof Integer))
            valueMap.forEach(item::mapValue);
    }

    private V currentValue() {
        if (currentValue != null) return currentValue;
        return optionHolder.get(option, valueType);
    }

}
