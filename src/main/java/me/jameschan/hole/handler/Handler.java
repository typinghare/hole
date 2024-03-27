package me.jameschan.hole.handler;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.extend.HoleApp;

/**
 * Provides an abstract base for command execution logic tied to a specific {@link CommandTemplate}.
 * This class is designed to be extended by concrete implementations that define how to handle
 * commands within an application context, using the parsed command information provided by
 * {@link Command} objects.
 */
public abstract class Handler {
    /**
     * The {@link CommandTemplate} associated with this handler.
     */
    public final CommandTemplate commandTemplate;

    /**
     * Constructs a new {@code Handler} with the specified {@link CommandTemplate}.
     * @param commandTemplate The {@link CommandTemplate} that describes the structure of the
     *                        command line this handler is designed to process.
     */
    protected Handler(final CommandTemplate commandTemplate) {
        this.commandTemplate = commandTemplate;
    }

    /**
     * Handles the command logic based on the given bundle and parsed command. Implementations of
     * this method should use the information contained in the {@link Command} object, such as
     * arguments and options, to perform the necessary operations within the bundle.
     * @param command The {@link Command} object representing the parsed command. This includes the
     *                command name, arguments, and options as defined by the {@code lineTemplate}.
     * @param bundle  The {@link Bundle} instance contains environment, status code, and a string
     *                buffer that stores the output from the execution.
     * @param app     The {@link HoleApp} instance.
     */
    public abstract void handle(final Command command, final Bundle bundle, final HoleApp app);
}