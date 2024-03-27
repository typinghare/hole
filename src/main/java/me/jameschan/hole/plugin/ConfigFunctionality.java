package me.jameschan.hole.plugin;

import me.jameschan.config.Config;

import java.util.Collection;

public interface ConfigFunctionality {
    /**
     * Returns configuration keys that are allowed to set of get by the application.
     * @return configuration keys.
     */
    Collection<String> configKeys();

    void setConfig(final Config config);
}
