package de.ariesbuildings.gui.guiitem.optionitem;

import com.google.common.collect.Lists;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.QuickItemStack;

import java.util.List;

public class BooleanOptionItem<OptionType extends Option> extends OptionItem<OptionType, Boolean> {

    protected BooleanOptionItem(Boolean currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        super(currentValue, optionHolder, option, content, slot);
    }

    @Override
    protected void updateCurrentValue() {
        currentValue = !currentValue;
    }

    @Override
    public void mapValue(Boolean value, QuickItemStack itemStack) {
        List<String> lore = Lists.newArrayList();
        lore.add("");
        if (value) {
            lore.add(I18n.translate("gui.player_settings.item.option.lore_true"));
        } else {
            lore.add(I18n.translate("gui.player_settings.item.option.lore_false"));
        }
        itemStack.setLore(lore);
        super.mapValue(value, itemStack);
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
