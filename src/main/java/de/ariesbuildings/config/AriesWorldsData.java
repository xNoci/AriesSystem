package de.ariesbuildings.config;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.config.serializers.AriesSerializers;
import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.RawLocation;
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.WorldVisibility;
import lombok.SneakyThrows;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class AriesWorldsData extends AbstractObjectData<AriesWorld> {

    public AriesWorldsData() {
        super("worlds.data.json", 1);
    }

    public List<String> getSavedWorlds() {
        return config().childrenMap().keySet().stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    void onSerialize(ConfigurationNode node, AriesWorld world) {
        setNodeValue(node, "creator", world.getWorldCreator());
        setNodeValue(node, "worldType", world.getType());
        setNodeValue(node, "visibility", world.getVisibility());
        setNodeValue(node, "builders", world.getBuilders(), AriesSerializers.Type.UUID_LIST);
        setNodeValue(node, "creationTime", world.getCreationTime());
        setNodeValue(node, "displayIcon", world.getDisplayIcon());
        setNodeValue(node, "worldSpawn", world.getWorldSpawn());
        setNodeValue(node, "options", world.getOptions().getOptions(), AriesSerializers.Type.WORLD_OPTION_MAP);
    }

    @Override
    void onDeserialize(ConfigurationNode node, AriesWorld world) {
        nodeValue(node, "creator", UUID.class).ifPresent(world::setWorldCreator);
        nodeValue(node, "worldType", WorldType.class).ifPresent(world::setType);
        nodeValue(node, "visibility", WorldVisibility.class).ifPresent(world::setVisibility);
        nodeListValue(node, "builders", UUID.class).ifPresent(world::setBuilders);
        nodeValue(node, "creationTime", Long.class).ifPresent(world::setCreationTime);
        nodeValue(node, "displayIcon", XMaterial.class).ifPresent(world::setDisplayIcon);
        nodeValue(node, "worldSpawn", RawLocation.class).ifPresent(world::setWorldSpawn);
        nodeValue(node, "options", AriesSerializers.Type.WORLD_OPTION_MAP).ifPresent(options -> world.getOptions().setOptions(options));
    }
}
