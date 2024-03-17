package me.jameschan.hole.extend;

import me.jameschan.hole.entry.EntryManager;
import me.jameschan.hole.handler.HandlerManager;
import me.jameschan.hole.plugin.PluginManager;
import me.jameschan.kernel.App;

import java.util.List;

/**
 * Hole application.
 */
public class HoleApp extends App {
    public HoleApp() {
        super(HoleApp.class, List.of(
            HandlerManager.class,
            EntryManager.class,
            PluginManager.class
        ));
    }
}
