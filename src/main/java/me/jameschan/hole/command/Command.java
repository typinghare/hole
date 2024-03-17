package me.jameschan.hole.command;

import java.util.List;

/**
 * Represents a parsed command line, encapsulating the template according to which it was parsed,
 * the arguments passed to the command, and the options specified along with their arguments. This
 * record is typically the result of parsing a command line string based on a predefined
 * {@link CommandTemplate}.
 * @param template The {@link CommandTemplate} that defines the structure of this command line,
 *                 including the command, required number of arguments, and allowed options.
 * @param args     A list of strings representing the arguments passed to the command. These are the
 *                 positional arguments that follow the command and precede any options.
 * @param options  A list of {@link Option} objects representing the options specified in the
 *                 command line, each with its own arguments as defined by its corresponding
 *                 {@link OptionTemplate}.
 */
public record Command(
    CommandTemplate template,
    List<String> args,
    List<Option> options
) {
    /**
     * Retrieves an option from this command line by its long label. If the specified option is
     * present among the parsed options, it is returned; otherwise, this method returns
     * {@code null}. This allows for easy retrieval of options when processing command lines.
     * @param longLabel The long label of the option to retrieve, corresponding to the
     *                  {@code longLabel} parameter of the option's {@link OptionTemplate}.
     * @return The {@link Option} object with the specified long label if present; otherwise,
     * {@code null}.
     */
    public Option getOption(final String longLabel) {
        for (final var option : options) {
            if (option.template().longLabel().equals(longLabel)) {
                return option;
            }
        }

        return null;
    }
}
