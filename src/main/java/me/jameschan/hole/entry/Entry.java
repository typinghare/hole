package me.jameschan.hole.entry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an entry with a unique ID and a set of key-value properties. This class allows for the
 * storage and retrieval of properties associated with an entry.
 */
public class Entry {
    /**
     * The unique identifier for this entry.
     */
    private final int id;

    /**
     * A map to store properties (key-value pairs) associated with this entry.
     */
    private final Map<String, String> properties = new HashMap<>();

    /**
     * Constructs a new Entry with the specified ID.
     * @param id The unique identifier for this entry.
     */
    public Entry(int id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier for this entry.
     * @return The unique ID of this entry.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a property (key-value pair) for this entry. If the property already exists, its value is
     * updated.
     * @param key   The property key to set.
     * @param value The property value to associate with the key.
     */
    public void set(final String key, final String value) {
        properties.put(key, value);
    }

    /**
     * Retrieves the value of a property by its key.
     * @param key The key of the property to retrieve.
     * @return The value associated with the specified key.
     * @throws KeyNotFoundException if the key does not exist in the properties map.
     */
    public String get(final String key) {
        return Optional.ofNullable(properties.get(key))
            .orElseThrow(() -> new KeyNotFoundException(key));
    }
}