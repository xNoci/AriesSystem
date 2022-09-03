package de.ariesbuildings.options;

sealed public interface Option permits PlayerOption, WorldOption {

    String getName();

    Class<?> getValueType();

    Object getDefaultValue();

    OptionType getType();

    Enum<? extends Option> getEnum();

    enum OptionType {
        WORLD_OPTION,
        PLAYER_OPTION
    }
}