package me.jameschan.hole.plugin.builtin.keyvalue;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.entry.EntryManager;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.handler.Handler;

public class KeyHandler extends Handler {
    protected final KeyValuePlugin keyValuePlugin;

    /**
     * Constructs a new {@code Handler} with the specified {@link CommandTemplate}.
     */
    protected KeyHandler(final KeyValuePlugin keyValuePlugin) {
        super(new CommandTemplate());
        this.keyValuePlugin = keyValuePlugin;
    }

    @Override
    public void handle(final Command command, final Bundle bundle, final HoleApp app) {
        final var key = command.args().getFirst();
        final var idList = keyValuePlugin.byKey.get(key);
        final var entryManager = app.use(EntryManager.class);
        idList.forEach(id -> {
            final var entry = entryManager.getById(id);
            bundle.buffer.append(entry.get("value")).append(System.lineSeparator());
        });
    }
}
