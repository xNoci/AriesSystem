package de.ariesbuildings.gui.optionitem;

import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;

public class BooleanOptionItem<OptionType extends Option> extends OptionItem<OptionType, Boolean> {

    protected BooleanOptionItem(Boolean currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        super(currentValue, optionHolder, option, content, slot);
    }

    @Override
    protected void updateCurrentValue() {
        currentValue = !currentValue;
    }
}
