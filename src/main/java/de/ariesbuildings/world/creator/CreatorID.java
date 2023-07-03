package de.ariesbuildings.world.creator;

import de.ariesbuildings.I18n;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

public enum CreatorID {

    IMPORTED(UUID.fromString("00000000-0000-01d0-0000-000000000001")),
    TUTORIAL(UUID.fromString("00000000-0000-01d0-0000-000000000002"));

    private final UUID uuid;
    @Getter private final String creatorName;

    CreatorID(UUID uuid) {
        this.uuid = uuid;
        this.creatorName = I18n.translate("world_creator." + name().toLowerCase());
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public static Optional<CreatorID> match(UUID uuid) {
        for (CreatorID value : values()) {
            if (value.uuid.equals(uuid)) return Optional.of(value);
        }
        return Optional.empty();
    }

}
