package tech.lemonlime.config;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;

public interface ConfigOption<T> {


    String name();

    String[] desc();

    Collection<String> suggestions();


    T value();


    Class<T> type();

    @NotNull
    T defaultValue();



    ConfigManager configManager();


    default boolean strict() {
        return false;
    }


    void set(String value) throws Exception;
    void set(T value) throws Exception;





}
