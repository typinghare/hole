package me.jameschan.hole.handler;

/**
 * Indicates that an attempt to retrieve or utilize an {@code Handler} for a specified command
 * failed because no such handler exists in the system. This exception is typically thrown when the
 * system is asked to handle or manipulate a command for which no associated handler has been
 * registered, highlighting a configuration or runtime logic error.
 */
public class HandlerNotFoundException extends RuntimeException {
    /**
     * Constructs a new {@code HandlerNotFoundException} with a message that identifies the command
     * for which a handler was expected but not found.
     * @param commandName The name of the command for which a handler was sought but not available.
     */
    public HandlerNotFoundException(final String commandName) {
        super(String.format("Handler not found: %s", commandName));
    }
}
