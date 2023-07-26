package de.ariesbuildings.world;

import de.ariesbuildings.world.creator.types.*;
import lombok.Getter;
import me.noci.quickutilities.utils.EnumUtils;

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
        return EnumUtils.asList(WorldType.class).stream()
                .filter(worldType -> !worldType.isInternalType())
                .toList();
    }

}
