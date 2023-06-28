package de.ariesbuildings.gui.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.EnumUtils;
import me.noci.quickutilities.utils.QuickItemStack;

import java.util.HashMap;

public class EnumOptionItem<O extends Option, T extends Enum<T>> extends OptionItem<O> {

    private final HashMap<T, QuickItemStack> enumMap = Maps.newHashMap();
    private T currentValue;

    public EnumOptionItem(T currentValue, OptionHolder<O> optionHolder, O option, InventoryContent content, int row, int column) {
        super(content, optionHolder, option, row, column);
        this.currentValue = currentValue;
    }

    @Override
    protected void click() {
        currentValue = EnumUtils.next(currentValue); //Rotate the enum to next entry
        QuickItemStack item = enumMap.get(currentValue); //Get item to display for currentValue

        setItem(item); //Set new display item
        setOption(currentValue); //Update the given option
    }

    public void setItem(T enumValue, QuickItemStack itemStack) {
        enumMap.put(enumValue, itemStack);
        if(enumValue == currentValue) setItem(itemStack); //TODO Maybe remove when builder exists
        update();
    }


}
