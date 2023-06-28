package de.ariesbuildings.gui.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.QuickItemStack;

import java.util.HashMap;

public class BooleanOptionItem<O extends Option> extends OptionItem<O> {

    private HashMap<Boolean, QuickItemStack> STATE_MAP = Maps.newHashMap();

    public BooleanOptionItem(InventoryContent content, OptionHolder<O> optionHolder, O option, int row, int column) {
        super(content, optionHolder, option, row, column);
    }

    @Override
    protected void click() {

    }

}
