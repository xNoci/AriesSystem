package de.ariesbuildings;

import de.ariesbuildings.locales.LanguageFile;
import me.noci.quickutilities.utils.Require;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class I18n {

    private static I18n instance;

    private final LanguageFile languageFile;

    public I18n(LanguageFile languageFile) {
        Require.checkState(instance == null, "Cannot create a second instance of I18n.");
        instance = this;
        this.languageFile = languageFile;
    }

    public static String translate(String key, Object... params) {
        if (StringUtils.isBlank(key))
            return "{Error: empty or null translation key}";
        if (instance == null) return key;
        return instance.format(key, params);
    }

    public static String prefix() {
        if (instance == null) return "AriesSystem - ";
        return instance.translate("prefix");
    }

    public static String noPermission() {
        if (instance == null) return "{No Permissions - No translation instance}";
        return instance.translate("noPermission");
    }

    public void delete() {
        instance = null;
    }

    private String translate(String key) {
        return languageFile.get(key);
    }

    private String format(String key, Object... params) {
        String translation = translate(key);
        translation = translation.replaceFirst("%prefix%", prefix());
        translation = translation.replaceAll("(?<!')'(?!')", "''"); //Fix single quote broke format
        return MessageFormat.format(translation, params);
    }

}
