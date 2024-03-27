package me.jameschan.hole.plugin.builtin.keyvalue;

import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.entry.EntryManager;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.handler.Handler;

import java.util.HashMap;

public class NewHandler extends Handler {
    protected KeyValuePlugin keyValuePlugin;

    /**
     * Constructs a new {@code Handler} with the specified {@link CommandTemplate}.
     */
    protected NewHandler(final KeyValuePlugin keyValuePlugin) {
        super(new CommandTemplate());
        this.keyValuePlugin = keyValuePlugin;
    }

    @Override
    public void handle(final Command command, final Bundle bundle, final HoleApp app) {
        final var args = command.args();
        final var key = args.getFirst();
        final var value = args.get(1);
        final var entryManager = app.use(EntryManager.class);
        final var entry = entryManager.create(new HashMap<>() {{
            put("key", key);
            put("value", value);
        }});

        // Register this to the plugin
        keyValuePlugin.register(key, entry.getId());

        bundle.buffer.append("You've created a new entry: key: ")
            .append(key).append(" ; value: ")
            .append(value);
    }
}
