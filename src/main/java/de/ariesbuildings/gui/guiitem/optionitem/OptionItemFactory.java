package de.ariesbuildings.gui.guiitem.optionitem;

import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import me.noci.quickutilities.utils.Require;

import java.util.HashMap;

public class OptionItemFactory {

    private static final HashMap<Class<?>, Factory<?>> FACTORY_MAP = Maps.newHashMap();

    static {
        FACTORY_MAP.put(Boolean.class, new BooleanOptionItem.Factory());
        FACTORY_MAP.put(Integer.class, new IntegerOptionItem.Factory());
        FACTORY_MAP.put(Enum.class, new EnumOptionItem.Factory());
    }

    public static <V, O extends Option> void build(Class<?> type, OptionItemBuilder<O, V> optionItemBuilder) {
        Factory factory = getFactory(type);
        factory.build(optionItemBuilder);
    }

    private static Factory<?> getFactory(Class<?> type) {
        if (type.isEnum()) return FACTORY_MAP.get(Enum.class);
        Require.checkState(FACTORY_MAP.containsKey(type), "Could not find OptionItemFactory for type %s.".formatted(type.getName()));
        return FACTORY_MAP.get(type);
    }


    public interface Factory<T> {
        <O extends Option> void build(OptionItemBuilder<O, T> builder);
    }

}
