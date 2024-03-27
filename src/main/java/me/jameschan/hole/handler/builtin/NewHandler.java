package me.jameschan.hole.handler.builtin;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.common.StatusCode;
import me.jameschan.hole.entry.EntryManager;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.handler.Handler;

import java.util.HashMap;

public class NewHandler extends Handler {
    public NewHandler() {
        super(new CommandTemplate(0));
    }

    @Override
    public void handle(final Command command, final Bundle bundle, final HoleApp app) {
        final var entryManager = app.use(EntryManager.class);
        final var entry = entryManager.create(new HashMap<>());

        bundle.statusCode = StatusCode.SUCCESS;
        bundle.buffer.append("Entry created: ").append(entry.getId());
    }
}
