package me.jameschan.hole.plugin.builtin.time;

import me.jameschan.hole.annotation.OverrideConstructor;
import me.jameschan.hole.entry.Entry;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.plugin.Plugin;

public class TimePlugin extends Plugin {
    @OverrideConstructor
    public TimePlugin(final HoleApp app) {
        super(app);
    }

    @Override
    public void onCreateEntry(final Entry entry) {
        entry.set("time", Long.toString(System.currentTimeMillis()));
    }
}
