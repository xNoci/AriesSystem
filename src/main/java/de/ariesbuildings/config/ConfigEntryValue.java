package de.ariesbuildings.config;

import lombok.Getter;

public record ConfigEntryValue(@Getter String fieldName, @Getter String configPath, @Getter String configValue) {

}
