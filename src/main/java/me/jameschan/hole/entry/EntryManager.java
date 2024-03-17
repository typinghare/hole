package me.jameschan.hole.entry;

import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages entries within the application, providing functionalities to create, retrieve, and manage
 * entries by their unique identifiers. This class serves as a central registry for all entries,
 * ensuring they are accessible throughout the application based on their IDs.
 */
public class EntryManager extends HoleManager {
    /**
     * A map that associates entry IDs with their corresponding {@link Entry} instances. This
     * facilitates efficient lookup of entries based on their unique identifiers.
     */
    private final Map<Integer, Entry> byId = new HashMap<>();

    /**
     * Tracks the highest ID assigned to an entry to ensure that each new entry receives a unique
     * identifier.
     */
    private Integer maxId = 0;

    /**
     * Constructs a new {@code EntryManager} instance, initializing it with a reference to the
     * application context.
     * @param app The {@link HoleApp} instance representing the current application context. This
     *            reference is used to pass along the application context to other components or
     *            functionalities that may require it.
     */
    public EntryManager(final HoleApp app) {
        super(app);
    }

    /**
     * Retrieves an entry by its ID.
     * @param id The unique identifier of the entry to retrieve.
     * @return The {@link Entry} associated with the specified ID.
     * @throws EntryNotFoundException if no entry exists with the provided ID.
     */
    public Entry getById(final Integer id) {
        return Optional.ofNullable(byId.get(id))
            .orElseThrow(() -> new EntryNotFoundException(id));
    }

    /**
     * Creates a new entry with the given data and assigns it a unique identifier. The new entry is
     * added to the registry, making it accessible through its ID.
     * @param data A map containing the data for the new entry. The structure of this data depends
     *             on the specific requirements of the {@link Entry} class.
     * @return The newly created {@link Entry} with a unique ID and populated with the provided
     * data.
     */
    public Entry create(final Map<String, String> data) {
        final var id = ++this.maxId;
        final var entry = new Entry(id);
        this.byId.put(id, entry);

        return entry;
    }
}