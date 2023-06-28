package de.ariesbuildings.gui.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;

import java.util.HashMap;

public abstract class OptionItem<OptionType extends Option, OptionValue> extends GuiItem {

    protected final HashMap<OptionValue, QuickItemStack> valueMap = Maps.newHashMap();

    protected OptionHolder<OptionType> optionHolder;
    protected OptionType option;
    protected OptionValue currentValue;

    private final InventoryContent content;
    private final int slot;

    public OptionItem(OptionValue currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int row, int column) {
        this.optionHolder = optionHolder;
        this.option = option;
        this.currentValue = currentValue;
        this.content = content;
        this.slot = Slot.getSlot(row, column);

        setAction(event -> {
            //TODO if (clickCondition != null && !clickCondition.shouldExecute()) return;
            click();
            update();
        });
        update();
    }

    protected abstract void click();

    protected void setOption(Object value) {
        optionHolder.set(option, value);
    }

    protected void update() {
        content.setItem(slot, this);
        content.applyContent();
    }

    public void mapValue(OptionValue value, QuickItemStack itemStack) {
        valueMap.put(value, itemStack);
        if(currentValue == value) setItem(itemStack);
        update();
    }

}
