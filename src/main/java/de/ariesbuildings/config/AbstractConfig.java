package de.ariesbuildings.config;

import com.google.common.collect.Lists;
import de.ariesbuildings.AriesSystem;
import lombok.SneakyThrows;
import me.noci.quickutilities.utils.Require;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class AbstractConfig {

    @SneakyThrows
    public static List<ConfigEntryValue> getEntries(Class<? extends AbstractConfig> configClass) {
        List<ConfigEntryValue> entries = Lists.newArrayList();

        for (Field field : configClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) continue;
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);

            String fieldName = field.getName();
            String configPath = entry.name();
            String configValue = "null";

            try {
                Object fieldValue = field.get(null);
                if (fieldValue != null) {
                    configValue = fieldValue.toString();
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                e.printStackTrace();
            }


            entries.add(new ConfigEntryValue(fieldName, configPath, configValue));
        }
        return entries;
    }

    private final int configVersion;

    protected AbstractConfig(String path, int configVersion) {
        this.configVersion = configVersion;

        Path configPath = Path.of(AriesSystem.getInstance().getDataFolder().getPath(), path);

        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .path(configPath)
                .build();

        if (!Files.exists(configPath)) {
            createNewConfig(configPath, loader);
            return;
        }

        loadConfig(loader);
    }

    @SneakyThrows
    private void createNewConfig(Path path, HoconConfigurationLoader loader) {
        CommentedConfigurationNode node = loader.createNode();

        CommentedConfigurationNode configVersionNode = node.node("configVersion");
        setConfigVersion(configVersionNode);

        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) continue;
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);
            CommentedConfigurationNode entryNode = node.node((Object[]) entry.name().split("\\."));

            Require.nonBlank(entry.comment()).ifPresent(entryNode::comment);

            try {
                entryNode.set(field.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Files.createDirectories(path.toAbsolutePath().getParent());
        loader.save(node);
    }

    @SneakyThrows
    private void loadConfig(HoconConfigurationLoader loader) {
        CommentedConfigurationNode node = loader.load();

        CommentedConfigurationNode configVersionNode = node.node("configVersion");
        setConfigVersion(configVersionNode);

        boolean modified = false;

        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) continue;
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);
            CommentedConfigurationNode entryNode = node.node((Object[]) entry.name().split("\\."));

            if (!entryNode.virtual()) {
                try {
                    field.set(null, entryNode.get(field.getType()));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                continue;
            }

            entryNode.comment(entry.comment());
            try {
                entryNode.set(field.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            modified = true;
        }

        if (modified) {
            loader.save(node);
        }
    }

    @SneakyThrows
    private void setConfigVersion(CommentedConfigurationNode configNode) {
        configNode.comment("This indicates the current version of this config file. Do not change this manually!");
        configNode.set(this.configVersion);
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface ConfigEntry {
        String name();

        String comment() default "";
    }

}
