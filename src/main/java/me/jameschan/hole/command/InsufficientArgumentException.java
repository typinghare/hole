package me.jameschan.hole.command;

/**
 * Exception thrown when the number of arguments provided for a command is insufficient according to
 * its {@link CommandTemplate}. This exception is used during the parsing of command lines to ensure
 * that all required arguments for a command are provided. If the provided arguments are fewer than
 * required, this exception is thrown to signal the discrepancy.
 */
public class InsufficientArgumentException extends RuntimeException {
    /**
     * This exception is crucial for maintaining the integrity of command execution, preventing
     * commands from running with incomplete input and potentially leading to unexpected behavior or
     * errors.
     * @param commandTemplate The {@link CommandTemplate} for the command that was being processed
     *                        when the exception was thrown. This template specifies the required
     *                        number of arguments for the command.
     * @param numArgsProvided The actual number of arguments that were provided for the command.
     *                        This number is compared against the {@code numArgs} property of the
     *                        {@code LineTemplate} to determine if the exception should be thrown.
     */
    public InsufficientArgumentException(
        final CommandTemplate commandTemplate,
        final int numArgsProvided
    ) {
        super(String.format(
            "Fail to make a command from the template due to insufficient arguments. " +
                "%d arguments are required, but only %d arguments are provided.",
            commandTemplate.numArgs(), numArgsProvided
        ));
    }
}
