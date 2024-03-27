package me.jameschan.hole.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginJar {
    final File file;

    public PluginJar(final String jarFileName) {
        file = new File(jarFileName);
    }

    public void loadPluginClass() throws MalformedURLException {
        final var url = file.toURI().toURL();
        try (final URLClassLoader classLoader = new URLClassLoader(new URL[]{url})) {
            final var className = file.getName() + "Plugin";
            classLoader.loadClass(className);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
