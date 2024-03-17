package me.jameschan.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A configuration class that stores settings or parameters. Only keys present in provided key set
 * are allowed, ensuring the configuration contains only predefined keys.
 */
public class Config implements Cloneable {
    /**
     * Set containing allowed key for configuration.
     */
    public final Set<String> keySet;

    /**
     * Internal storage for configuration data
     */
    private final Map<String, Object> data = new HashMap<>();

    /**
     * Constructs a new Config instance with specified allowed keys.
     * @param keySet The set of keys that are allowed in this configuration.
     */
    public Config(final Set<String> keySet) {
        this.keySet = keySet;
    }

    /**
     * Retrieves the value associated with the specified key.
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or {@code null} if this map contains
     * no mapping for the key.
     */
    public Object get(final String key) {
        return data.get(key);
    }

    /**
     * Inserts or updates the value associated with the specified key. Only keys present in the
     * keySet are allowed; attempts to insert other keys will result in an IllegalKeyException.
     * @param key   The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @throws IllegalKeyException If the key is not present in the predefined set of allowed keys.
     */
    public void set(final String key, final Object value) {
        if (!keySet.contains(key)) {
            throw new IllegalKeyException(key);
        }

        data.put(key, value);
    }

    /**
     * Returns the data of this config.
     * @return the data of this config.
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Loads data into the configuration. Only keys present in the keySet are allowed; attempts to
     * insert other keys will result in an exception.
     * @param newData The data to be loaded into the configuration.
     * @throws IllegalArgumentException If any key is not present in the predefined set of allowed
     *                                  keys.
     */
    public void load(final Map<String, Object> newData) {
        newData.forEach(this::set);
    }

    @SuppressWarnings({"MethodDoesntCallSuperMethod", "CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    protected Config clone() {
        final Config clonedConfig = new Config(this.keySet);
        clonedConfig.data.putAll(new HashMap<>(this.data));

        return clonedConfig;
    }
}
