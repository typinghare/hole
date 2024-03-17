package me.jameschan.hole.command;

/**
 * Exception class for handling cases where insufficient arguments are supplied for an option. This
 * class extends {@link RuntimeException} to indicate a runtime problem related to option argument
 * processing.
 */
public class OptionInsufficientArgumentException extends RuntimeException {
    /**
     * Constructs a new {@code OptionInsufficientArgumentException} with a detailed message. The
     * message includes the label of the option that was attempted to be made, the number of
     * arguments it requires, and the number of arguments that were actually provided.
     * @param optionTemplate  The {@link OptionTemplate} object representing the option that
     *                        triggered this exception. It contains details about the expected
     *                        option, such as its label and the required number of arguments.
     * @param numArgsProvided The actual number of arguments that were provided to the option. This
     *                        number is insufficient compared to what is required, leading to the
     *                        exception being thrown.
     */
    public OptionInsufficientArgumentException(
        final OptionTemplate optionTemplate,
        final int numArgsProvided
    ) {
        super(String.format(
            "Fail to make an option from the template due to insufficient arguments. " +
                "The label of the option is: %s. " +
                "%d arguments are required, but only %d arguments are provided.",
            optionTemplate.longLabel(), optionTemplate.numArgs(), numArgsProvided
        ));
    }
}
