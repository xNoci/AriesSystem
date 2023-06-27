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
    @SneakyThrows
    void onSerialize(ConfigurationNode objectNode, AriesWorld world) {
        objectNode.node("creator").set(world.getWorldCreator());
        objectNode.node("worldType").set(world.getType());
        objectNode.node("visibility").set(world.getVisibility());
        objectNode.node("builders").set(AriesSerializers.Type.UUID_LIST, world.getBuilders());
        objectNode.node("creationTime").set(world.getCreationTime());
        objectNode.node("displayIcon").set(world.getDisplayIcon());
        objectNode.node("worldSpawn").set(world.getWorldSpawn());
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

        WorldVisibility visibility = objectNode.node("visibility").get(WorldVisibility.class);
        if(visibility != null) {
            world.setVisibility(visibility);
        }

        ArrayList<UUID> builders = (ArrayList<UUID>) objectNode.node("builders").getList(UUID.class);
        if (builders != null) {
            world.setBuilders(builders);
        }

        Long creationTime = objectNode.node("creationTime").get(Long.class);
        if (creationTime != null) {
            world.setCreationTime(creationTime);
        }

        XMaterial displayIcon = objectNode.node("displayIcon").get(XMaterial.class);
        if (displayIcon != null) {
            world.setDisplayIcon(displayIcon);
        }

        RawLocation worldSpawn = objectNode.node("worldSpawn").get(RawLocation.class);
        if (worldSpawn != null) {
            world.setWorldSpawn(worldSpawn);
        }

        OptionMap<WorldOption> options = objectNode.node("options").get(AriesSerializers.Type.WORLD_OPTION_MAP);
        if (options != null) {
            world.getOptions().setOptions(options);
        }

    }
}
