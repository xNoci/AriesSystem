package de.ariesbuildings.locales;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import lombok.SneakyThrows;
import me.noci.quickutilities.utils.Require;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class LanguageLoader {

    private static final String MESSAGES_PROPERTIES = "locales/messages.properties";
    private static final Path LANGUAGE_FOLDER = Paths.get(AriesSystem.getInstance().getDataFolder().getPath(), "language");
    private static final Path LANGUAGE_FILE = Paths.get(LANGUAGE_FOLDER.toString(), "messages.lang");

    private static I18n i18n;

    public static void initialise() {
        tryToCreate();
        loadLocals();
    }

    public static void loadLocals() {
        if (i18n != null) {
            i18n.delete();
        }

        LanguageFile file = new LanguageFile();
        i18n = new I18n(file);
    }

    @SneakyThrows
    private static void tryToCreate() {
        if (!Files.exists(LANGUAGE_FOLDER)) {
            AriesSystemConfig.info("Creating language folder");
            Files.createDirectory(LANGUAGE_FOLDER);
        }

        if (!Files.exists(LANGUAGE_FILE)) {
            createNewLanguageFile();
            return;
        }

    }

    @SneakyThrows
    private static void createNewLanguageFile() {
        if (Files.exists(LANGUAGE_FILE)) {
            String backupName = backupFileName();
            AriesSystemConfig.debug("Creating backup of old 'messages.lang' as '%s' before deleting it", backupName);
            Files.copy(LANGUAGE_FILE, Path.of(LANGUAGE_FOLDER.toString(), backupName));
            Files.delete(LANGUAGE_FILE);
        }

        AriesSystemConfig.info("Creating new '%s' file", LANGUAGE_FILE.getFileName());
        try (InputStream resourceFile = AriesSystem.getInstance().getResource(MESSAGES_PROPERTIES)) {
            Require.nonNull(resourceFile, "Cannot create language file, could not find 'locales/messages.properties'");
            Files.copy(resourceFile, LANGUAGE_FILE);
        }
    }

    protected static void readeDefaultProperties(Properties properties) {
        try (InputStreamReader defaultProperties = new InputStreamReader(AriesSystem.getInstance().getResource(MESSAGES_PROPERTIES), StandardCharsets.UTF_8)) {
            properties.load(defaultProperties);
        } catch (Exception e) {
            AriesSystemConfig.debug("Failed to load default properties: " + e.getMessage());
        }
    }

    protected static void readeServerProperties(Properties properties) {
        try (InputStreamReader defaultProperties = new InputStreamReader(Files.newInputStream(LANGUAGE_FILE), StandardCharsets.UTF_8)) {
            properties.load(defaultProperties);
        } catch (Exception e) {
            AriesSystemConfig.debug("Failed to load server messages.lang: " + e.getMessage());
        }
    }

    private static String backupFileName() {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return "messages_" + dateTime.format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss")) + ".lang";
    }

}
