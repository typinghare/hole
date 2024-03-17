package me.jameschan.hole.plugin;

import me.jameschan.hole.command.TokenIterator;
import me.jameschan.hole.common.Bundle;

public interface ExecutionAopFunctionality {
    /**
     * Prepares the environment before executing a command. This method is invoked to perform any
     * necessary preliminary operations, such as setting up command-specific configurations,
     * validating tokens, or initializing resources required for the command execution.
     * <p>
     * The {@code bundle} parameter provides access to application-specific data and utilities that
     * may be needed before the command execution. The {@code tokenIterator} offers a way to inspect
     * and manipulate the command line tokens that will be used in the command execution process.
     * @param bundle        The {@code Bundle} containing application-specific data and utilities.
     *                      This object may be modified or queried to prepare for command
     *                      execution.
     * @param tokenIterator The {@code TokenIterator} for accessing and inspecting the command line
     *                      tokens. This iterator can be used to pre-process or validate tokens
     *                      before execution.
     */
    void beforeExecute(final Bundle bundle, final TokenIterator tokenIterator);
}
