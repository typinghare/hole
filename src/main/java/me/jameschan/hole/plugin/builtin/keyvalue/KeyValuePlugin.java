package me.jameschan.hole.plugin.builtin.keyvalue;

import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.plugin.Plugin;

public class KeyValuePlugin extends Plugin {
    /**
     * Constructs an HoleAppBased object.
     * @param app The application instance this object is based upon.
     */
    public KeyValuePlugin(final HoleApp app) {
        super(app);
    }

    @Override
    protected void initHandlers() {
        registerHandler("new", new NewEntryHandler(), true);
    }
}
