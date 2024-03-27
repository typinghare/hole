package me.jameschan.hole.plugin;

import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleManager;
import me.jameschan.hole.plugin.builtin.keyvalue.KeyValuePlugin;
import me.jameschan.hole.plugin.builtin.time.TimePlugin;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * Manages plugins within an application, handling registration, enabling, disabling, and traversal
 * of plugins. This manager extends {@code HoleManager} to leverage application context-specific
 * functionalities. It maintains a collection of plugins, allowing for dynamic manipulation based on
 * runtime conditions.
 */
public class PluginManager extends HoleManager {
    /**
     * Plugin loader.
     */
    private final PluginLoader pluginLoader;

    /**
     * A mapping of plugin class names to their respective instances.
     */
    private final Map<String, Plugin> byClassName = new HashMap<>();

    /**
     * A set of currently enabled plugins.
     */
    private final Set<Plugin> enabledPluginSet = new HashSet<>();

    /**
     * Constructs a {@code PluginManager} object, linking it with the application context.
     * @param app The application instance this manager is associated with.
     */
    public PluginManager(final HoleApp app) {
        super(app);
        this.pluginLoader = new PluginLoader(app);
    }

    @Override
    public void init() {
        super.init();
        load(KeyValuePlugin.class);
        load(TimePlugin.class);

//        final var classpath = System.getProperty("java.class.path");
//        final var classpathArray = classpath.split(":");
//        Arrays.stream(classpathArray).forEach(System.out::println);
    }

    /**
     * Loads a plugin class.
     * @param Class A class that extends the {@code Plugin} class in this package.
     */
    public void load(final Class<? extends Plugin> Class) {
        try {
            final var plugin = pluginLoader.loadByClass(Class);
            plugin.init();
            byClassName.put(Class.getName(), plugin);
        } catch (final RuntimeException e) {
            throw new RuntimeException("Fail to load and initialize plugin: " + Class.getName(), e);
        }
    }

    /**
     * Enables a plugin by its name, making it active within the application.
     * @param pluginName The name of the plugin to enable.
     */
    public void enable(final String pluginName) {
        final var plugin = byClassName.get(pluginName);
        if (plugin != null) {
            enabledPluginSet.add((Plugin) plugin.useThis());
        }
    }

    /**
     * Disables a plugin by its name, removing it from the set of active plugins.
     * @param pluginName The name of the plugin to disable.
     */
    public void disable(final String pluginName) {
        final var plugin = byClassName.get(pluginName);
        enabledPluginSet.remove(plugin);
    }

    /**
     * Returns an iterable collection of all enabled plugins.
     * @return An iterable of enabled plugins.
     */
    public Iterable<Plugin> getEnabledPlugins() {
        return enabledPluginSet;
    }

    /**
     * Performs an action on each enabled plugin.
     * @param callback The action to perform on each plugin.
     */
    public void forEachEnabled(final Consumer<Plugin> callback) {
        StreamSupport.stream(getEnabledPlugins().spliterator(), false)
            .forEach(callback);
    }
}
