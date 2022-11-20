package de.ariesbuildings.world.creator.types;

public enum WorldType {

    VOID(new VoidWorldCreatorType());

    private final WorldCreatorType creatorType;

    WorldType(WorldCreatorType creatorType) {
        this.creatorType = creatorType;
    }

    public WorldCreatorType getCreator() {
        return creatorType;
    }

}
