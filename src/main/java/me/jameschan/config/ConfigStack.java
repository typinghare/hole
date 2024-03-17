package me.jameschan.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a stack of configurations at different levels. This allows for overriding configurations
 * at different levels of specificity.
 */
public class ConfigStack {
    /**
     * Default level label.
     */
    public static final String DEFAULT_LEVEL = "DEFAULT";

    /**
     * Mapping from levels to configuration instances.
     */
    private final Map<String, Config> byLevel = new HashMap<>();

    /**
     * Stack of levels indicating the order in which they were added.
     */
    private final List<String> levelStack = new ArrayList<>();

    /**
     * Represents the current dynamic configuration that reflects the combined settings of all
     * levels.
     */
    private final Config dynamicConfig;

    /**
     * Constructs a new ConfigStack instance with a default configuration.
     * @param defaultConfig The default configuration.
     */
    public ConfigStack(final Config defaultConfig) {
        this.byLevel.put(DEFAULT_LEVEL, defaultConfig);
        this.levelStack.add(DEFAULT_LEVEL);
        this.dynamicConfig = defaultConfig.clone();
    }

    /**
     * Creates and registers a new configuration level.
     * @param level The level of the configuration.
     * @return The newly created Config instance for the specified level.
     */
    public Config createConfig(final String level) {
        final var newConfig = dynamicConfig.clone();
        byLevel.put(level, newConfig);
        levelStack.add(level);

        return newConfig;
    }

    /**
     * Loads data into the dynamic configuration.
     * @param newData The data to load into the dynamic configuration.
     */
    public void load(final Map<String, Object> newData) {
        this.dynamicConfig.load(newData);
    }

    /**
     * Loads data into the configuration at a specified level and propagates changes to higher
     * levels.
     * @param newData The data to load into the configuration.
     * @param level   The level at which to apply the new data.
     */
    public void load(final Map<String, Object> newData, final String level) {
        load(newData, levelStack.indexOf(level));
    }

    /**
     * Recursively loads data starting from a given level index.
     * @param newData    The data to be loaded.
     * @param levelIndex The index of the level from which to start loading the data.
     */
    public void load(final Map<String, Object> newData, final int levelIndex) {
        if (levelIndex < 0 || levelIndex >= levelStack.size()) {
            return;
        }

        get(levelIndex).load(newData);
        if (levelIndex == levelStack.size() - 1) {
            dynamicConfig.load(newData);
        }

        load(newData, levelIndex + 1);
    }

    /**
     * Updates the dynamic configuration by iteratively putting the data of each configuration
     * associated with the levels stored in the level stack. This method iterates through each level
     * in the level stack, retrieves the corresponding configuration, and merges its data into the
     * dynamic configuration.
     */
    public void update() {
        for (final var level : levelStack) {
            final var config = byLevel.get(level);
            dynamicConfig.getData().putAll(config.getData());
        }
    }

    /**
     * Retrieves the configuration for a specified level.
     * @param level The level for which to retrieve the configuration.
     * @return The configuration instance for the specified level.
     * @throws RuntimeException if the level does not exist.
     */
    public Config get(final String level) {
        if (level == null) {
            return dynamicConfig;
        } else {
            final Config levelConfig = byLevel.get(level);
            if (levelConfig != null) {
                return levelConfig;
            }

            throw new RuntimeException("Level does not exist: " + level);
        }
    }

    /**
     * Retrieves the dynamic configuration.
     * @return the dynamic configuration.
     */
    public Config getDynamic() {
        return get(null);
    }

    /**
     * Retrieves the configuration at a given index in the level stack.
     * @param levelIndex The index of the level.
     * @return The configuration at the specified index.
     * @throws IllegalArgumentException if the level index is out of bounds.
     */
    public Config get(final int levelIndex) {
        if (levelIndex < 0 || levelIndex >= levelStack.size()) {
            throw new IllegalArgumentException("Illegal level index: " + levelIndex);
        }

        return get(levelStack.get(levelIndex));
    }
}
