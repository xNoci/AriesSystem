package de.ariesbuildings.locales;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Properties;

public class LanguageFile {

    private final Map<String, String> languageKeys;

    public LanguageFile() {
        Properties properties = new Properties();

        LanguageLoader.readeDefaultProperties(properties);
        LanguageLoader.readeServerProperties(properties);

        languageKeys = Maps.fromProperties(properties);
    }

    public String get(String key) {
        return languageKeys.getOrDefault(key, key);
    }

    public boolean hasKey(String key) {
        return languageKeys.containsKey(key);
    }


}
