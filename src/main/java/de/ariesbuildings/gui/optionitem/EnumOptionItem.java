package de.ariesbuildings.gui.optionitem;

import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.EnumUtils;
import me.noci.quickutilities.utils.QuickItemStack;

public class EnumOptionItem<OptionType extends Option, OptionValue extends Enum<OptionValue>> extends OptionItem<OptionType, OptionValue> {

    public EnumOptionItem(OptionValue currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int row, int column) {
        super(currentValue, optionHolder, option, content, row, column);
        this.currentValue = currentValue;
    }

    @Override
    protected void click() {
        currentValue = EnumUtils.next(currentValue); //Rotate the enum to next entry
        QuickItemStack item = valueMap.get(currentValue); //Get item to display for currentValue

        setItem(item); //Set new display item
        setOption(currentValue); //Update the given option
    }


}
