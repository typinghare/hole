package me.jameschan.hole.plugin;

import me.jameschan.hole.entry.Entry;

public interface EntryModificationFunctionality {
    /**
     * Called when entry is created.
     * @param entry The entry is created.
     */
    void onCreateEntry(final Entry entry);
}
