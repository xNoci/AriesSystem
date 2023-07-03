package de.ariesbuildings.gui.optionitem;

import de.ariesbuildings.I18n;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import lombok.Setter;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.QuickItemStack;

public class IntegerOptionItem<OptionType extends Option> extends OptionItem<OptionType, Integer> {

    private QuickItemStack integerItem;
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
    protected void updateDisplayedItem() {
        if (integerItem == null) {
            super.updateDisplayedItem();
            return;
        }
        integerItem.setAmount(currentValue);
        integerItem.setLore("", I18n.translate("gui.option_item.integer_item.current_value", currentValue));
        setItem(integerItem);
    }

    public void setIntegerItem(QuickItemStack integerItem) {
        this.integerItem = integerItem;
        updateDisplayedItem();
    }
}
