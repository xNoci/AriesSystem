package de.ariesbuildings.gui.guiitem.optionitem;

import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.EnumUtils;
import org.bukkit.event.inventory.ClickType;

public class EnumOptionItem<OptionType extends Option> extends OptionItem<OptionType, Enum<?>> {

    protected EnumOptionItem(Enum<?> currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        super(currentValue, optionHolder, option, content, slot);
    }

    @Override
    protected boolean updateCurrentValue(ClickType clickType) {
        if (clickType != ClickType.RIGHT && clickType != ClickType.LEFT) return false;
        currentValue = clickType == ClickType.LEFT ? EnumUtils.next(currentValue) : EnumUtils.previous(currentValue);
        return true;
    }

    public static class Factory implements OptionItemFactory.Factory<Enum<?>> {
        @Override
        public <O extends Option> OptionItem<O, Enum<?>> build(OptionItemBuilder<O, Enum<?>> builder) {
            EnumOptionItem<O> item = new EnumOptionItem<>(builder.currentValue(), builder.optionHolder, builder.option, builder.content, builder.slot);
            item.setClickCondition(builder.clickCondition);
            builder.valueMap.forEach(item::mapValue);
            return item;
        }
    }

}
