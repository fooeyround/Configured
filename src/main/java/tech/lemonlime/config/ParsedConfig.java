package tech.lemonlime.config;

import org.apache.commons.lang3.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public final class ParsedConfig<T> implements ConfigOption<T>, Comparable<ConfigOption<?>> {
    private static final Map<Class<?>, FromStringConverter<?>> CONVERTER_MAP = Map.ofEntries(
            Map.entry(String.class, str -> str),
            Map.entry(Boolean.class, str -> switch (str) {
                case "true" -> true;
                case "false" -> false;
                default -> throw new Exception("Invalid boolean value");
            }),
            numericalConverter(Integer.class, Integer::parseInt),
            numericalConverter(Double.class, Double::parseDouble),
            numericalConverter(Long.class, Long::parseLong),
            numericalConverter(Float.class, Float::parseFloat)
    );


    private final Field field;
    private final String name;

    private final String description;

    private final List<String> options;
    public boolean isStrict;

    private final Class<T> type;

    private final T defaultValue;


    private final FromStringConverter<T> converter;
    private final ConfigManager configManager; // to rename to settingsManager

    //TODO: implement this.
    @Override
    public int compareTo(@NotNull ConfigOption<?> o) {
        return 0;
    }


    @FunctionalInterface
    interface FromStringConverter<T> {
        T convert(String value) throws Exception;
    }

    record RuleAnnotation(String desc, String[] options, boolean strict) {
    }


    public static <T> ConfigOption<T> of(Field field, ConfigManager configManager) {
        RuleAnnotation rule;
        if (field.isAnnotationPresent(Option.class)) {
            Option a = field.getAnnotation(Option.class);
            rule = new RuleAnnotation(a.desc(), a.options(), a.strict());
        } else {
            throw new IllegalStateException("[Configured config] Option annotation not present on passed field");
        }
        return new ParsedConfig<>(field, rule, configManager);
    }

    ParsedConfig(Field field, RuleAnnotation ruleAnnotation, ConfigManager configManager) {

        this.name = field.getName();
        this.field = field;
        @SuppressWarnings("unchecked") // We are "defining" T here
        Class<T> type = (Class<T>) ClassUtils.primitiveToWrapper(field.getType());
        this.type = type;
        this.isStrict = ruleAnnotation.strict();

        this.configManager = configManager;

        this.defaultValue = value();

        FromStringConverter<T> converter0 = null;


        if (ruleAnnotation.options().length > 0) {
            this.options = List.of(ruleAnnotation.options());
        } else if (this.type == Boolean.class) {
            this.options = List.of("true", "false");
        } else if (this.type.isEnum()) {
            this.options = Arrays.stream(this.type.getEnumConstants()).map(e -> ((Enum<?>) e).name().toLowerCase(Locale.ROOT)).toList();
            converter0 = str -> {
                try {
                    @SuppressWarnings({"unchecked", "rawtypes"}) // Raw necessary because of signature. Unchecked because compiler doesn't know T extends Enum
                    T ret = (T) Enum.valueOf((Class<? extends Enum>) type, str.toUpperCase(Locale.ROOT));
                    return ret;
                } catch (IllegalArgumentException e) {
                    throw new Exception("Valid values for this option are: " + this.options);
                }
            };
        } else {
            this.options = List.of();
        }


        if (isStrict && !this.options.isEmpty()) {
            //TODO: make this work
        }
        if (converter0 == null) {
            @SuppressWarnings("unchecked")
            FromStringConverter<T> converterFromMap = (FromStringConverter<T>) CONVERTER_MAP.get(type);
            if (converterFromMap == null)
                throw new UnsupportedOperationException("Unsupported type for ParsedConfig" + type);
            converter0 = converterFromMap;
        }
        this.converter = converter0;


        this.description = ruleAnnotation.desc();

    }



    @Override
    public void set(String value) throws Exception {
        set(converter.convert(value));
    }

    @Override
    public void set(T value) throws Exception {
        if (!value.equals(value())) {
            try {
                this.field.set(null, value);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Couldn't access field for rule: " + name, e);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParsedConfig && ((ParsedConfig<?>) obj).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.name + ": " + ConfigHelper.toRuleString(value());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String[] desc() {
        return new String[0];
    }


    @Override
    public Collection<String> suggestions() {
        return options;
    }

    @Override
    public ConfigManager configManager() {
        return configManager;
    }

    @Override
    @SuppressWarnings("unchecked") // T comes from the field
    public T value() {
        try {
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            // Can't happen at regular runtime because we'd have thrown it on construction
            throw new IllegalArgumentException("Couldn't access field for rule: " + name, e);
        }
    }



    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public @NotNull T defaultValue() {
        return defaultValue;
    }



    @Override
    public boolean strict() {
        return this.isStrict;
    }

    private static <T> Map.Entry<Class<T>, FromStringConverter<T>> numericalConverter(Class<T> outputClass, Function<String, T> converter) {
        return Map.entry(outputClass, str -> {
            try {
                return converter.apply(str);
            } catch (NumberFormatException e) {
                throw new Exception("Invalid number for rule");
            }
        });
    }


    @Deprecated(forRemoval = true)
    public T get() {
        return value();
    }

    @Deprecated(forRemoval = true)
    public String getAsString() {
        return ConfigHelper.toRuleString(value());
    }


    @Deprecated(forRemoval = true)
    public boolean getBoolValue() {
        return ConfigHelper.getBooleanValue(this);
    }


    @Deprecated(forRemoval = true)
    public boolean isDefault() {
        return ConfigHelper.isInDefaultValue(this);
    }


    @Deprecated(forRemoval = true)
    public void resetToDefault() {
        ConfigHelper.resetToDefault(this);
    }






}
