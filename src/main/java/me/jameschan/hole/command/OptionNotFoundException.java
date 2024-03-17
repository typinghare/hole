package me.jameschan.hole.command;

/**
 * Exception thrown when an option template cannot be found for a specified label within a
 * {@link CommandTemplate}. This usually occurs when parsing a command line string and the input
 * includes an option that does not match any of the option templates defined in the
 * {@code LineTemplate} for that command.
 */
public class OptionNotFoundException extends RuntimeException {
    /**
     * This exception helps in diagnosing issues with command line parsing, especially in cases
     * where the input might include typos or unsupported options.
     * @param template    The {@link CommandTemplate} being used when the exception is thrown. It
     *                    provides context by indicating within which command's template the missing
     *                    option was sought.
     * @param optionLabel The label (long or short) of the option that was not found. This is the
     *                    identifier used in the command line input that does not have a
     *                    corresponding  {@link OptionTemplate}.
     */
    public OptionNotFoundException(final CommandTemplate template, final String optionLabel) {
        super(String.format("Option template not found. " +
                "There is no option template for \"%s\"",
            optionLabel
        ));
    }
}
