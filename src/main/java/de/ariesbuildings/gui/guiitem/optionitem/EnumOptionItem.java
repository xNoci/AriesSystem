package de.ariesbuildings.gui.guiitem.optionitem;

import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.EnumUtils;

public class EnumOptionItem<OptionType extends Option, OptionValue extends Enum<OptionValue>> extends OptionItem<OptionType, OptionValue> {

    protected EnumOptionItem(OptionValue currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        super(currentValue, optionHolder, option, content, slot);
    }

    @Override
    protected void updateCurrentValue() {
        currentValue = EnumUtils.next(currentValue); //Rotate the enum to next entry
    }

    public static class Factory<T extends Enum<T>> implements OptionItemFactory.Factory<T> {
        @Override
        public <O extends Option> void build(OptionItemBuilder<O, T> builder) {
            EnumOptionItem<O, T> item = new EnumOptionItem<>(builder.currentValue(), builder.optionHolder, builder.option, builder.content, builder.slot);
            item.setClickCondition(builder.clickCondition);
            builder.valueMap.forEach(item::mapValue);
        }
    }

}
