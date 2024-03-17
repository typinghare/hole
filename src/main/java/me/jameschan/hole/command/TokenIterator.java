package me.jameschan.hole.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An iterator designed to parse and iterate over a set of tokens derived from command line input.
 * This iterator is capable of handling both short and long option formats, and uniquely, it can
 * split grouped short options into individual tokens for more granular access.
 * <p>
 * Short options are expected to be prefixed with a single hyphen ("-") and can be grouped together
 * (e.g., "-abc" is equivalent to "-a -b -c"). Long options should be prefixed with two hyphens
 * ("--") and are not grouped (e.g., "--option").
 * <p>
 * This iterator also provides functionality to peek at the next token without advancing the
 * iterator, check if a token is an option, determine if a token is a long option, and extract the
 * label part of an option token.
 */
public class TokenIterator implements Iterator<String> {
    /**
     * A list to hold the individual tokens after parsing the command line arguments.
     */
    private final List<String> tokenList = new ArrayList<>();

    /**
     * The current position in the token list, used to track iteration progress.
     */
    private int i = 0;

    /**
     * Constructs a new TokenIterator by parsing an array of command line arguments. Grouped short
     * options are split into separate tokens, while long options and non-option arguments are added
     * as-is.
     * @param plainArgList A list of command line arguments to be parsed into tokens.
     */
    public TokenIterator(final List<String> plainArgList) {
        for (final var plainArg : plainArgList) {
            if (TokenIterator.isLongOption(plainArg)) {
                tokenList.add(plainArg);
            } else if (TokenIterator.isOption(plainArg)) {
                for (final char option : plainArg.substring(1).toCharArray()) {
                    tokenList.add("-" + option);
                }
            } else {
                tokenList.add(plainArg);
            }
        }
    }

    /**
     * Returns the next token in the iteration without advancing the iteration.
     * @return The next token if available; otherwise, {@code null}.
     */
    public String peek() {
        return this.hasNext() ? tokenList.get(i) : null;
    }

    @Override
    public boolean hasNext() {
        return i < tokenList.size();
    }

    @Override
    public String next() {
        return hasNext() ? tokenList.get(i++) : null;
    }

    /**
     * Checks if a token is an option, which starts with a hyphen.
     * @param token the token to check.
     * @return true if the token is an option; false otherwise.
     */
    public static boolean isOption(final String token) {
        return token.startsWith("-");
    }

    /**
     * Checks if a token is a long option, which starts with two hyphens.
     * @param token the token to check.
     * @return true if the token is a long option; false otherwise.
     */
    public static boolean isLongOption(final String token) {
        return token.startsWith("--");
    }

    /**
     * Extracts the label part of an option token.
     * @param token the option token.
     * @return the label part of the option token.
     */
    public static String getOptionLabel(final String token) {
        return token.charAt(1) == '-' ? token.substring(2) : token.substring(1);
    }
}
