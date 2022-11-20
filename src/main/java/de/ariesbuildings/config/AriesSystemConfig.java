package de.ariesbuildings.config;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.util.List;

public class AriesSystemConfig extends AbstractConfig {

    @ConfigEntry(name = "debug", comment = "When enabled the plugin will print various debug messages")
    public static boolean DEBUG = false;

    public static void load() {
        new AriesSystemConfig();
    }

    @SneakyThrows
    public static List<Triple<String, String, String>> getAllEntries() {
        List<Triple<String, String, String>> entries = Lists.newLinkedList();

        for (Field field : AriesSystemConfig.class.getDeclaredFields()) {
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


            entries.add(Triple.of(fieldName, configPath, configValue));
        }
        return entries;
    protected AriesSystemConfig() {
        super("config.hocon", 1);
    }

}
