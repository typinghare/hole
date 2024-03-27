package me.jameschan.hole.plugin;

public class PluginLoadingException extends RuntimeException {
    public PluginLoadingException(final Exception exception) {
        super(exception);
    }
}
