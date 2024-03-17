package me.jameschan.hole.command;

import java.util.List;

/**
 * Represents an option parsed from command line arguments or configuration files. This record
 * encapsulates an option's template, which defines the expected structure of the option, and the
 * list of arguments that were actually provided for this option.
 * @param template The template for the option, defining its expected behavior and requirements.
 * @param args     A list of strings representing the arguments provided to the option. The nature
 *                 and number of these arguments are determined by the {@code OptionTemplate}.
 */
public record Option(
    OptionTemplate template,
    List<String> args
) {
}
