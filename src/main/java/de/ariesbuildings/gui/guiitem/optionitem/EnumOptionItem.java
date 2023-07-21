package de.ariesbuildings.gui.guiitem.optionitem;

import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.EnumUtils;

public class EnumOptionItem<OptionType extends Option> extends OptionItem<OptionType, Enum<?>> {

    protected EnumOptionItem(Enum<?> currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        super(currentValue, optionHolder, option, content, slot);
    }

    @Override
    protected void updateCurrentValue() {
        currentValue = EnumUtils.next(currentValue); //Rotate the enum to next entry
    }

    public static class Factory implements OptionItemFactory.Factory<Enum<?>> {
        @Override
        public <O extends Option> void build(OptionItemBuilder<O, Enum<?>> builder) {
            EnumOptionItem<O> item = new EnumOptionItem<>(builder.currentValue(), builder.optionHolder, builder.option, builder.content, builder.slot);
            item.setClickCondition(builder.clickCondition);
            builder.valueMap.forEach(item::mapValue);
        }
    }

}
