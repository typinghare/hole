package me.jameschan.hole.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the template for an option, specifying its labels and the required number of arguments.
 * An {@code OptionTemplate} is used to create {@code Option} instances that conform to this
 * template, ensuring that options are processed and created consistently according to predefined
 * specifications.
 * @param longLabel  The long label of the option (e.g., "--optionName") used for identification in
 *                   a command line.
 * @param shortLabel The short label of the option (e.g., "-o") used for a more concise
 *                   identification in a command line.
 * @param numArgs    The number of arguments this option requires. This number does not include the
 *                   option label itself, but strictly the additional parameters that follow it.
 */
public record OptionTemplate(
    String longLabel,
    char shortLabel,
    int numArgs
) {
    /**
     * Constructs an {@code OptionTemplate} with the specified long label, short label, and the
     * default number of arguments, which is 0.
     * @param longLabel  The long label of the option.
     * @param shortLabel The short label of the option.
     */
    public OptionTemplate(final String longLabel, char shortLabel) {
        this(longLabel, shortLabel, 0);
    }

    /**
     * Constructs an {@code OptionTemplate} with the specified long label and automatically assigns
     * the first character of the long label as the short label.
     * @param longLabel The long label of the option.
     */
    public OptionTemplate(final String longLabel) {
        this(longLabel, longLabel.charAt(0));
    }

    /**
     * Creates an {@code Option} based on this template by consuming arguments from the given
     * {@code TokenIterator}. This method iterates over the {@code TokenIterator}, extracting a
     * number of arguments equal to {@code numArgs} specified in this template. If the number of
     * available arguments is less than {@code numArgs}, it throws an
     * {@code OptionInsufficientArgumentException}.
     * @param tokenIterator A {@code TokenIterator} representing a sequence of tokens (arguments)
     *                      from which this option's arguments should be extracted. It is expected
     *                      that this iterator contains at least {@code numArgs} arguments following
     *                      the current position; otherwise, an exception will be thrown.
     * @return An {@code Option} instance that contains the extracted arguments and a reference to
     * this template.
     * @throws OptionInsufficientArgumentException If there are insufficient arguments available in
     *                                             {@code tokenIterator} to satisfy the number of
     *                                             arguments required by this template.
     */
    public Option make(final TokenIterator tokenIterator) {
        final List<String> args = new ArrayList<>();
        for (int i = 0; i < numArgs; ++i) {
            final var next = tokenIterator.next();
            if (next == null) {
                throw new OptionInsufficientArgumentException(this, i);
            }

            args.add(next);
        }

        return new Option(this, args);
    }
}
