package me.jameschan.hole.plugin;

import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleManager;
import me.jameschan.hole.plugin.builtin.keyvalue.KeyValuePlugin;
import me.jameschan.hole.plugin.builtin.server.ServerPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * A mapping of plugin classes to their respective instances.
     */
    private final Map<Class<? extends Plugin>, Plugin> byClass = new HashMap<>();

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
    }

    @Override
    public void init() {
        super.init();

        // Load all plugins
        final List<String> pluginNameList = new ArrayList<>() {{
            add(ServerPlugin.class.getName());
            add(KeyValuePlugin.class.getName());
        }};

        pluginNameList.forEach(this::loadPlugin);
        final var classpath = Paths.get(System.getProperty("java.class.path") + "me.jameschan.plugin.builtin");
        System.out.println(classpath.toString());
//        loadPluginDir(classpath);
    }

    /**
     * Registers a plugin with this manager, initializing it in the process.
     * @param plugin The plugin to register.
     */
    public void register(final Plugin plugin) {
        byClass.put(plugin.getClass(), plugin);

        plugin.init();
    }

    public void loadPlugin(final String pluginName) {
        try {
            final Class<?> pluginClass = Class.forName(pluginName);
            if (!Plugin.class.isAssignableFrom(pluginClass)) {
                throw new IllegalArgumentException("Class " + pluginName + " is not a Plugin");
            }

            @SuppressWarnings("unchecked") final Class<Plugin> pluginType = (Class<Plugin>) pluginClass;

            Constructor<Plugin> constructor;
            try {
                constructor = pluginType.getDeclaredConstructor(HoleApp.class);
            } catch (NoSuchMethodException e) {
                throw new NoSuchMethodException("Constructor HoleApp.class not found in " + pluginName);
            }

            Plugin pluginInstance;
            try {
                constructor.setAccessible(true); // In case the constructor is not public
                pluginInstance = constructor.newInstance(app);
            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException("Failed to instantiate plugin: " + pluginName, e);
            }

            register(pluginInstance);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Plugin class not found: " + e.getMessage(), e);
        } catch (IllegalArgumentException | NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void loadPluginDir(final Path pluginDirPath) {
        try (final var directoryStream = Files.newDirectoryStream(pluginDirPath)) {
            for (Path path : directoryStream) {
                if (!Files.isDirectory(path)) continue;

                System.out.println(path.getFileName().toFile().getAbsoluteFile());
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a plugin by its name.
     * @param pluginName The name of the plugin to retrieve.
     * @return The plugin instance associated with the given name.
     * @throws NoSuchElementException If no plugin with the given name is found.
     */
    public Plugin getPluginByName(final String pluginName) {
        return byClass.keySet().stream()
            .filter(pluginClass -> pluginClass.getSimpleName().equals(pluginName))
            .findFirst()
            .map(byClass::get)
            .orElseThrow();
    }

    /**
     * Enables a plugin by its name, making it active within the application.
     * @param pluginName The name of the plugin to enable.
     */
    public void enable(final String pluginName) {
        final var plugin = getPluginByName(pluginName);

        enabledPluginSet.add((Plugin) plugin.useThis());
    }

    /**
     * Disables a plugin by its name, removing it from the set of active plugins.
     * @param pluginName The name of the plugin to disable.
     */
    public void disable(final String pluginName) {
        final var plugin = getPluginByName(pluginName);
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
