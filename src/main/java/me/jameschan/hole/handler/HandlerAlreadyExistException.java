package me.jameschan.hole.handler;

/**
 * Signals that an attempt to register a new {@code Handler} for a command that already has an
 * associated handler in the system has been made. This exception is thrown to prevent the
 * duplication of command handlers, ensuring that each command has a unique handler.
 */
public class HandlerAlreadyExistException extends RuntimeException {

    /**
     * Constructs a new {@code ExecutorAlreadyExistException} with a detailed message that includes
     * the name of the command for which a handler already exists.
     * @param command The name of the command for which a handler registration was attempted, but a
     *                handler already exists.
     */
    public HandlerAlreadyExistException(final String command) {
        super(String.format("Executor already exists: %s", command));
    }
}