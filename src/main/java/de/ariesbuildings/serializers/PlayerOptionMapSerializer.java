package de.ariesbuildings.serializers;

import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.PlayerOption;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;

public class PlayerOptionMapSerializer implements TypeSerializer<OptionMap<PlayerOption>> {

    static final PlayerOptionMapSerializer INSTANCE = new PlayerOptionMapSerializer();

    private static final String OPTIONS = "playerOptions";

    private PlayerOptionMapSerializer() {
    }

    private ConfigurationNode nonVirtualNode(final ConfigurationNode source, final Object... path) throws SerializationException {
        if (!source.hasChild(path)) {
            throw new SerializationException("Required field " + Arrays.toString(path) + " was not present in node");
        }
        return source.node(path);
    }

    @Override
    public OptionMap<PlayerOption> deserialize(Type type, ConfigurationNode source) throws SerializationException {
        OptionMap<PlayerOption> optionMap = new OptionMap<>();
        ConfigurationNode options = nonVirtualNode(source, OPTIONS);

        for (PlayerOption option : PlayerOption.values()) {
            if (!options.hasChild(option.name())) continue;
            ConfigurationNode optionNode = options.node(option.name());

            var value = optionNode.get(option.getValueType());
            optionMap.set(option, value);
        }

        return optionMap;
    }

    @Override
    public void serialize(Type type, @Nullable OptionMap<PlayerOption> optionMap, ConfigurationNode target) throws SerializationException {
        if (optionMap == null) return;
        ConfigurationNode node = target.node(OPTIONS);

        for (PlayerOption option : optionMap.getKeys()) {
            Class<?> optionType = optionMap.getType(option);
            ConfigurationNode optionNode = node.node(option.name());

            if (optionType.isPrimitive()) {
                optionNode.set(optionMap.get(option, optionType));
            } else {
                optionNode.set(optionType, optionMap.get(option, optionType));
            }
        }

    }
}
