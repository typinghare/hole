package me.jameschan.hole.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the template for a command, specifying the required number of arguments, and a list of
 * permissible option templates. This template is used to parse and validate a command, ensuring
 * that it conforms to the expected format and contains the required options and arguments.
 */
public class CommandTemplate {
    /**
     * The fixed number of arguments expected after the command.
     */
    private final int numArgs;

    /**
     * A list of {@link OptionTemplate} objects that define the options this command can accept.
     */
    protected final List<OptionTemplate> optionTemplateList = new ArrayList<>();

    /**
     * Constructs a new {@code CommandTemplate} with a specified number of arguments.
     * @param numArgs The fixed number of arguments expected after the command. This does not
     *                include options.
     */
    public CommandTemplate(final int numArgs) {
        this.numArgs = numArgs;
    }

    /**
     * Constructs a new {@code CommandTemplate} that assumes no arguments for this command.
     */
    public CommandTemplate() {
        this(0);
    }

    /**
     * Gets the fixed number of arguments expected after the command.
     * @return The fixed number of arguments expected after the command. This does not include
     * options.
     */
    public int numArgs() {
        return numArgs;
    }

    /**
     * Adds an {@link OptionTemplate} to the list of permissible options for this command template.
     * @param optionTemplate The {@link OptionTemplate} to add to the list of permissible options.
     */
    public void addOptionTemplate(final OptionTemplate optionTemplate) {
        this.optionTemplateList.add(optionTemplate);
    }

    /**
     * Creates a {@code Command} object by parsing tokens from the given {@code TokenIterator}. This
     * method sequentially consumes tokens to collect command arguments and options as defined by
     * this template. If an option token is encountered, it attempts to match it with one of the
     * option templates in {@code optionTemplateList} and creates an {@code Option} object
     * accordingly. This process continues until there are no more tokens or until the necessary
     * arguments and options have been collected.
     * @param tokenIterator A {@code TokenIterator} that provides a sequence of tokens to be parsed
     *                      into a {@code Command}.
     * @return A {@code Command} object that represents the parsed command line, including arguments
     * and options.
     * @throws InsufficientArgumentException If the number of arguments parsed is less than
     *                                       {@code numArgs} required by this template.
     * @throws OptionNotFoundException       If an option is encountered that does not match any of
     *                                       the templates in {@code optionTemplateList}.
     */
    public Command make(final TokenIterator tokenIterator) {
        final List<String> args = new ArrayList<>();
        final List<Option> optionList = new ArrayList<>();
        while (tokenIterator.hasNext()) {
            final var next = tokenIterator.next();

            if (TokenIterator.isOption(next)) {
                final var optionLabel = TokenIterator.getOptionLabel(next);
                final var optionTemplate = findOptionTemplate(optionLabel);
                optionList.add(optionTemplate.make(tokenIterator));
            } else {
                args.add(next);
            }
        }

        if (args.size() < numArgs) {
            throw new InsufficientArgumentException(this, args.size());
        }

        return new Command(this, args, optionList);
    }

    /**
     * Searches for an {@link OptionTemplate} in {@code optionTemplateList} that matches the given
     * option label. This method supports both long and short labels as specified by the option
     * label argument.
     * @param optionLabel The label of the option to find. It could be a long or short label.
     * @return The matching {@link OptionTemplate} if found.
     * @throws OptionNotFoundException If no matching option template is found in
     *                                 {@code optionTemplateList}.
     */
    private OptionTemplate findOptionTemplate(final String optionLabel) {
        final boolean canBeShortLabel = optionLabel.length() == 1;
        final char firstChar = optionLabel.charAt(0);
        for (final var optionTemplate : optionTemplateList) {
            if (optionTemplate.longLabel().equals(optionLabel)) {
                return optionTemplate;
            }

            if (canBeShortLabel && optionTemplate.shortLabel() == firstChar) {
                return optionTemplate;
            }
        }

        throw new OptionNotFoundException(this, optionLabel);
    }
}
