package me.jameschan.hole.plugin.builtin.keyvalue;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.handler.Handler;

public class NewEntryHandler extends Handler {
    /**
     * Constructs a new {@code Handler} with the specified {@link CommandTemplate}.
     */
    protected NewEntryHandler() {
        super(new CommandTemplate());
    }

    @Override
    public void handle(final Command command, final Bundle bundle) {
        final var args = command.args();
        final var key = args.getFirst();
        final var value = args.get(1);

        bundle.buffer.append("You've created a new entry!").append(key).append(value);
    }
}
