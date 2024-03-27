package me.jameschan.hole.handler.builtin;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.handler.Handler;

/**
 * Finds an entry with associated ID.
 */
public class ItemHandler extends Handler {
    protected ItemHandler() {
        super(new CommandTemplate(1));
    }

    @Override
    public void handle(final Command command, final Bundle bundle, final HoleApp app) {
        final var idString = command.args().getFirst();
        final var id = Integer.parseInt(idString);
    }
}
