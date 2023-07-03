package de.ariesbuildings.gui.optionitem;

import me.noci.quickutilities.inventory.SlotClickEvent;

@FunctionalInterface
public interface ClickCondition {

    boolean shouldExecute(SlotClickEvent event);

}
