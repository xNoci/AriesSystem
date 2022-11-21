package de.ariesbuildings.world.creator.types;

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
