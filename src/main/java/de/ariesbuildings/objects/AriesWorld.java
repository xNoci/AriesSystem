package de.ariesbuildings.objects;

import com.google.common.collect.Sets;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.utils.OptionHolder;
import org.bukkit.World;

import java.util.HashSet;
import java.util.UUID;

public class AriesWorld extends OptionHolder<WorldOption> {

    private final World world;
    private UUID worldCreator;  //TODO Aus Config holen
    private final HashSet<UUID> builders = Sets.newHashSet();//TODO Aus Config holen

    protected AriesWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public boolean isBuilder(UUID uuid) {
        return builders.contains(uuid) || worldCreator.equals(uuid);
    }
    }

    @Override
    protected OptionChangeEvent createOptionChangeEvent(WorldOption option, Object oldValue, Object newValue) {
        return new OptionChangeEvent(option, oldValue, newValue, world);
    }
}
