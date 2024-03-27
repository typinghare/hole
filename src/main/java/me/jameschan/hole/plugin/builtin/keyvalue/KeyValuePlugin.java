package me.jameschan.hole.plugin.builtin.keyvalue;

import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyValuePlugin extends Plugin {
    protected Map<String, List<Integer>> byKey = new HashMap<>();

    /**
     * Constructs an HoleAppBased object.
     * @param app The application instance this object is based upon.
     */
    public KeyValuePlugin(final HoleApp app) {
        super(app);
    }

    @Override
    protected void initHandlers() {
        registerHandler("new", new NewHandler(this), true);
        registerHandler("key", new KeyHandler(this), true);
    }

    public void register(final String key, final int id) {
        final List<Integer> idList = byKey.computeIfAbsent(key, k -> new ArrayList<>());
        idList.add(id);
    }
}
