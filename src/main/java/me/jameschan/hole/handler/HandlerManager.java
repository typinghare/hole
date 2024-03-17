package me.jameschan.hole.handler;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.command.TokenIterator;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.common.StatusCode;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleManager;
import me.jameschan.hole.handler.builtin.DefaultHandler;
import me.jameschan.hole.plugin.PluginManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manages the registration and handling of command handlers within an application context. This
 * class serves as a central point for associating command strings with their corresponding
 * {@link Handler} instances and for dispatching command handling requests to the appropriate
 * handler based on input arguments.
 */
public class HandlerManager extends HoleManager {
    /**
     * A mapping of command strings to their respective {@link Handler} instances.
     */
    private final Map<String, Handler> byName = new HashMap<>();

    /**
     * Constructs a new {@code HandlerManager} instance, initializing it with a reference to the
     * application context.
     * @param app The {@link HoleApp} instance representing the current application context. This
     *            reference is used to pass along the application context to executors during
     *            command execution.
     */
    public HandlerManager(final HoleApp app) {
        super(app);
    }

    @Override
    public void init() {
        super.init();
        initBuiltinHandlers();
    }

    /**
     * Initializes builtin handlers.
     */
    private void initBuiltinHandlers() {
        this.registerHandler(null, new DefaultHandler(), false);
    }

    /**
     * Registers an {@link Handler} with this manager, associating it with the command specified in
     * the executor's {@link CommandTemplate}. If the {@code cover} parameter is {@code false} and
     * an executor is already registered for the command, an {@link HandlerAlreadyExistException} is
     * thrown.
     * @param name     The name of the command to be handled by the handler.
     * @param handler  The {@link Handler} to be registered.
     * @param override If {@code true}, the new handler will replace any existing handler for the
     *                 same command. If {@code false}, an exception is thrown when attempting to
     *                 register an executor for a command that already has one.
     * @throws HandlerAlreadyExistException if {@code cover} is {@code false} and an executor is
     *                                      already registered for the command.
     */
    public void registerHandler(final String name, final Handler handler, final boolean override) {
        if (!override && byName.containsKey(name)) {
            throw new HandlerAlreadyExistException(name);
        }

        byName.put(name, handler);
    }

    /**
     * Retrieves the {@link Handler} associated with the specified command name. This method enables
     * the lookup of handlers based on command names, facilitating command handling and other
     * operations where handler access is required.
     * @param command The command string used to identify the handler.
     * @return The {@link Handler} associated with the specified command name.
     * @throws HandlerNotFoundException if no handler is found for the given command name, ensuring
     *                                  that callers are made aware of the absence of a handler for
     *                                  the requested command.
     */
    public Handler getHandler(final String command) {
        return Optional.ofNullable(byName.get(command))
            .orElseThrow(() -> new HandlerNotFoundException(command));
    }

    /**
     * Executes the command logic based on the provided {@link Bundle} and {@link TokenIterator}.
     * This method determines the command to execute based on the next token in the token iterator,
     * retrieves the corresponding handler, and delegates the command handling to the handler.
     * <p>
     * If the token iterator contains a valid command name, it retrieves the corresponding handler
     * and invokes its {@link Handler#handle(Command, Bundle)} method with a command object created
     * from the token iterator and the provided bundle.
     * <p>
     * The command handling process involves parsing the command line tokens into a {@link Command}
     * object using the command template associated with the handler. The handler's {@code handle}
     * method is responsible for processing the parsed command.
     * @param bundle        The {@link Bundle} instance containing the application context, status
     *                      code, and output buffer.
     * @param tokenIterator The {@link TokenIterator} providing a sequence of tokens representing
     *                      the command line input to be executed.
     * @throws NullPointerException if the {@code tokenIterator} is {@code null}.
     */
    public void execute(final Bundle bundle, final TokenIterator tokenIterator) {
        final var hasCommand = tokenIterator.hasNext() &&
            !TokenIterator.isOption(tokenIterator.peek());
        final var commandName = hasCommand ? tokenIterator.next() : null;
        final var handler = getHandler(commandName);

        handler.handle(handler.commandTemplate.make(tokenIterator), bundle);
    }

    /**
     * Executes a command based on the provided list of raw arguments. This method processes the raw
     * arguments, creates a bundle, allows enabled plugins to execute pre-command logic, executes
     * the command if it has not been executed yet, allows plugins to perform post-command
     * operations, and prints the output if available.
     * <p>
     * The method first creates a {@link TokenIterator} from the raw arguments to facilitate token
     * processing. It then creates a new {@link Bundle} instance to store the application context,
     * status code, and output buffer.
     * <p>
     * Enabled plugins are given the opportunity to execute logic before and after the command
     * execution. Plugins implementing
     * {@link me.jameschan.hole.plugin.Plugin#beforeExecute(Bundle, TokenIterator)} are invoked
     * before the command execution. If the command has not been executed yet (indicated by a
     * {@link StatusCode#NULL} status code in the bundle), the command is executed.
     * <p>
     * After the command execution, plugins implementing
     * {@link me.jameschan.hole.plugin.Plugin#beforePrint(Bundle)} are given the opportunity to
     * print additional information from the bundle. If the output buffer is not empty, it is
     * printed to the console.
     * @param rawArgs The list of raw arguments representing the command to execute.
     */
    public void executeRawArgs(final List<String> rawArgs) {
        // Create token iterator from raw arguments
        final var tokenIterator = new TokenIterator(rawArgs);

        // Create a bundle
        final var bundle = Bundle.create();

        // Let enabled plugins execute the command first
        final var pluginManager = app.use(PluginManager.class);
        pluginManager.forEachEnabled(plugin -> plugin.beforeExecute(bundle, tokenIterator));

        // If the status code is NULL, it means that the command is yet executed
        if (bundle.statusCode == StatusCode.NULL) {
            execute(bundle, tokenIterator);
        }

        // Let enabled plugins print the bundle content first
        pluginManager.forEachEnabled((plugin) -> plugin.beforePrint(bundle));

        // If the buffer string is not empty, print it on the console
        final var message = bundle.buffer.toString().trim();
        if (!message.isEmpty()) {
            System.out.println(message);
        }
    }
}