package de.ariesbuildings.world;

import com.google.common.collect.Lists;
import de.ariesbuildings.world.creator.types.*;
import lombok.Getter;

import java.util.List;

public enum WorldType {

    VOID(new VoidWorldCreatorType()),
    NORMAL(new NormalWorldCreatorType()),
    FLAT(new FlatWorldCreatorType()),
    IMPORTED(new NormalWorldCreatorType()),
    INTERNAL_TUTORIAL(new TutorialWorldCreatorType(), true);

    @Getter private final WorldCreatorType creator;
    @Getter private final boolean internalType;

    WorldType(WorldCreatorType creatorType) {
        this(creatorType, false);
    }

    WorldType(WorldCreatorType creator, boolean internalType) {
        this.creator = creator;
        this.internalType = internalType;
    }

    public static List<WorldType> publicTypes() {
        List<WorldType> types = Lists.newArrayList();

        for (WorldType value : values()) {
            if (!value.internalType) types.add(value);
        }
        return types;
    }

    public static WorldType[] publicTypesArray() {
        return publicTypes().toArray(WorldType[]::new);
    }

}
