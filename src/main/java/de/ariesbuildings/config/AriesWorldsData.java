package de.ariesbuildings.config;

import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.options.OptionMap;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.serializers.AriesSerializers;
import de.ariesbuildings.world.creator.types.WorldType;
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
    @SneakyThrows
    void onSerialize(ConfigurationNode objectNode, AriesWorld world) {
        objectNode.node("creator").set(world.getWorldCreator());
        objectNode.node("worldType").set(world.getType());
        objectNode.node("builders").set(AriesSerializers.Type.UUID_LIST, world.getBuilders());
        objectNode.node("creationTime").set(world.getCreationTime());
        objectNode.node("options").set(AriesSerializers.Type.WORLD_OPTION_MAP, world.getOptions().getOptions());
    }

    @Override
    @SneakyThrows
    void onDeserialize(ConfigurationNode objectNode, AriesWorld world) {
        UUID worldCreator = objectNode.node("creator").get(UUID.class);
        if (worldCreator != null) {
            world.setWorldCreator(worldCreator);
        }

        WorldType worldType = objectNode.node("worldType").get(WorldType.class);
        if (worldType != null) {
            world.setType(worldType);
        }

        ArrayList<UUID> builders = (ArrayList<UUID>) objectNode.node("builders").getList(UUID.class);
        if (builders != null) {
            world.setBuilders(builders);
        }

        Long creationTime = objectNode.node("creationTime").get(Long.class);
        if (creationTime != null) {
            world.setCreationTime(creationTime);
        }

        OptionMap<WorldOption> options = objectNode.node("options").get(AriesSerializers.Type.WORLD_OPTION_MAP);
        if (options != null) {
            world.getOptions().setOptions(options);
        }

    }
}
