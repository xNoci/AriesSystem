package de.ariesbuildings.gui.guiitem.optionitem;

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

    public static class Factory implements OptionItemFactory.Factory<Boolean> {
        @Override
        public <O extends Option> void build(OptionItemBuilder<O, Boolean> builder) {
            BooleanOptionItem<O> item = new BooleanOptionItem<>(builder.currentValue(), builder.optionHolder, builder.option, builder.content, builder.slot);
            item.setClickCondition(builder.clickCondition);
            builder.valueMap.forEach(item::mapValue);
        }
    }

}
