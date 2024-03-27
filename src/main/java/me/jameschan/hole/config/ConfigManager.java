package me.jameschan.hole.config;

import me.jameschan.config.Config;
import me.jameschan.config.ConfigStack;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleManager;
import me.jameschan.hole.plugin.PluginManager;

import java.util.HashSet;

/**
 * Config manager.
 */
public class ConfigManager extends HoleManager {
    // Built-in config keys
    public static final String KEY_USER_CONFIG = "user.config";
    public static final String KEY_PLUGIN_PATH = "plugin.path";
    public static final String KEY_PLUGIN_ENABLED = "plugin.enabled";

    /**
     * Configuration stack.
     */
    public final ConfigStack configStack;

    /**
     * Constructs a ConfigManager object.
     * @param app The application instance this object is based upon.
     */
    public ConfigManager(final HoleApp app) {
        super(app);

        // Plugin manager
        final var pluginManager = app.use(PluginManager.class);

        // Set up configuration keys
        final var keySet = new HashSet<String>() {{
            add(KEY_USER_CONFIG);
            add(KEY_PLUGIN_PATH);
            add(KEY_PLUGIN_ENABLED);

            pluginManager.forEachEnabled(plugin -> addAll(plugin.configKeys()));
        }};

        // Configuration values
        final var defaultConfig = new Config(keySet) {{
            set(KEY_USER_CONFIG, null);
            set(KEY_PLUGIN_PATH, null);
            set(KEY_PLUGIN_ENABLED, null);

            pluginManager.forEachEnabled(plugin -> plugin.setConfig(this));
        }};
        configStack = new ConfigStack(defaultConfig);
    }

    public Config getDynamic() {
        return configStack.getDynamic();
    }

//    /**
//     * Loads configuration from a specific file.
//     * @param filename The filename.
//     * @param level    The level of the configuration.
//     */
//    public void loadFromFile(final String filename, final String level) {
//        if (!getFileExtension(filename).equals("json")) {
//            throw new IllegalArgumentException("Do not support non-JSON files.");
//        }
//
//        final var file = new File(filename);
//        if (!file.exists()) {
//            final var message = String.format("File not found: %s", file.getAbsoluteFile());
//            throw new RuntimeException(new FileNotFoundException(message));
//        } else if (file.isDirectory()) {
//            final var message = String.format(
//                "The give filename is a directory: %s",
//                file.getAbsoluteFile()
//            );
//            throw new RuntimeException(new FileNotFoundException(message));
//        }
//
//        try {
//            final var content = Files.readString(file.toPath());
//            final var config = this.configStack.createConfig(level);
//            final Map<String, Object> mapObject
//                = new Gson().fromJson(content, JsonTypes.MapStringObject);
//            config.load(mapObject);
//        } catch (final IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
