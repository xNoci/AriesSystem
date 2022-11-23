package de.ariesbuildings.world;

import de.ariesbuildings.world.creator.types.FlatWorldCreatorType;
import de.ariesbuildings.world.creator.types.NormalWorldCreatorType;
import de.ariesbuildings.world.creator.types.VoidWorldCreatorType;
import de.ariesbuildings.world.creator.types.WorldCreatorType;

public enum WorldType {

    VOID(new VoidWorldCreatorType()),
    NORMAL(new NormalWorldCreatorType()),
    FLAT(new FlatWorldCreatorType()),
    IMPORTED(new NormalWorldCreatorType());

    private final WorldCreatorType creatorType;

    WorldType(WorldCreatorType creatorType) {
        this.creatorType = creatorType;
    }

    public WorldCreatorType getCreator() {
        return creatorType;
    }

}
