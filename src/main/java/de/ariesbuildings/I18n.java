package de.ariesbuildings;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {

    private static final String BUNDLE_NAME = "locales.messages";
    private static I18n instance;

    private ResourceBundle defaultBundle;

    public I18n() {
        instance = this;
        defaultBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH);
    }

    public static String translate(String key, Object... params) {
        if (StringUtils.isBlank(key))
            return "{Error: empty or null translation key}";
        if (instance == null) return key;
        return instance.format(key, params);
    }

    public static String prefix() {
        if (instance == null) return "";
        return instance.translate("prefix");
    }

    public static String noPermission() {
        if (instance == null) return "";
        return instance.translate("noPermission");
    }

    public void disable() {
        instance = null;
    }

    private String translate(String key) {
        if (!defaultBundle.containsKey(key)) return key;
        return defaultBundle.getString(key);
    }

    private String format(String key, Object... params) {
        String translation = translate(key);
        translation = translation.replaceFirst("%prefix%", prefix());
        return MessageFormat.format(translation, params);
    }

}
