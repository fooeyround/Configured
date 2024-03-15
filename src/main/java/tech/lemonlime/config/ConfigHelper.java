package tech.lemonlime.config;

import java.util.Locale;

public class ConfigHelper {
    private ConfigHelper() {}


    public static boolean getBooleanValue(ConfigOption<?> option) {
        if (option.type() == Boolean.class) return (boolean) option.value();
        if (Number.class.isAssignableFrom(option.type())) return ((Number) option.value()).doubleValue() > 0;
        return false;
    }



    public static String toRuleString(Object value) {
        if (value instanceof Enum) return ((Enum<?>) value).name().toLowerCase(Locale.ROOT);
        return value.toString();
    }


    public static boolean isInDefaultValue(ConfigOption<?> option) {
        return option.defaultValue().equals(option.value());
    }


    public static <T> void resetToDefault(ConfigOption<T> option) {
        try {
            option.set(option.defaultValue());
        } catch (Exception e) {
            throw new IllegalStateException("Rule couldn't be set to default value!", e);
        }
    }


}
