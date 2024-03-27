package me.jameschan.hole.plugin;

import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleAppBased;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads plugin.
 */
public class PluginLoader extends HoleAppBased {
    PluginLoader(final HoleApp app) {
        super(app);
    }

    public Plugin loadByClassName(final String className) {
        try {
            @SuppressWarnings("unchecked") final Class<? extends Plugin> PluginClass
                = (Class<? extends Plugin>) Class.forName(className);
            return loadByClass(PluginClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }
    }

    public Plugin loadByClass(final Class<? extends Plugin> Class) {
        final String pluginName = Class.getName();

        if (!Plugin.class.isAssignableFrom(Class)) {
            throw new IllegalArgumentException("Class does not extend Plugin: " + pluginName);
        }

        Constructor<? extends Plugin> constructor;
        try {
            constructor = Class.getDeclaredConstructor(HoleApp.class);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException("Constructor should take HoleApp as parameter: "
                + pluginName, e);
        }

        Plugin pluginInstance;
        try {
            constructor.setAccessible(true);
            pluginInstance = constructor.newInstance(app);
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate plugin: " + pluginName, e);
        }

        return pluginInstance;
    }

    public List<Plugin> loadPluginDir(final Path dirPath) {
        final var pluginList = new ArrayList<Plugin>();

        try (final var directoryStream = Files.newDirectoryStream(dirPath)) {
            for (final Path path : directoryStream) {
                if (!Files.isDirectory(path)) continue;
                System.out.println(path.getFileName().toFile().getAbsoluteFile());
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return pluginList;
    }
}
