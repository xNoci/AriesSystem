package de.ariesbuildings.gui.guiitem.optionitem;

import de.ariesbuildings.I18n;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import lombok.Setter;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.QuickItemStack;

public class IntegerOptionItem<OptionType extends Option> extends OptionItem<OptionType, Integer> {

    @Setter private QuickItemStack integerItem;
    @Setter private int lowerBound = 0;
    @Setter private int upperBound = 100;
    @Setter private int increment = 1;

    protected IntegerOptionItem(int currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        super(currentValue, optionHolder, option, content, slot);
    }

    @Override
    protected void updateCurrentValue() {
        currentValue += increment;
        if (currentValue > upperBound) {
            currentValue = lowerBound;
        }
    }

    @Override
    protected QuickItemStack getItem() {
        if (integerItem == null) {
            return super.getItem();
        }
        QuickItemStack item = (QuickItemStack) integerItem.clone();
        item.setAmount(currentValue);
        item.setLore("",
                I18n.translate("gui.option_item.integer_item.current_value", currentValue),
                I18n.translate("gui.player_settings.item.option.lore_increment_left", currentValue));
        return item;
    }

    public static class Factory implements OptionItemFactory.Factory<Integer> {
        @Override
        public <O extends Option> void build(OptionItemBuilder<O, Integer> builder) {
            IntegerOptionItem<O> item = new IntegerOptionItem<>(builder.currentValue(), builder.optionHolder, builder.option, builder.content, builder.slot);

            if (builder.lowerBound > builder.upperBound) {
                int tmp = builder.lowerBound;
                builder.lowerBound = builder.upperBound;
                builder.upperBound = tmp;
            }

            if (builder.lowerBound >= 0) item.setLowerBound(builder.lowerBound);
            if (builder.upperBound >= 0) item.setUpperBound(builder.upperBound);
            if (builder.increment > 0) item.setIncrement(builder.increment);
            if (builder.integerItem != null) {
                item.setIntegerItem(builder.integerItem);
                item.updateDisplayedItem();
            }
            item.setClickCondition(builder.clickCondition);
        }
    }

}
