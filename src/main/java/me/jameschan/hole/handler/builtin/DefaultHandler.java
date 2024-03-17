package me.jameschan.hole.handler.builtin;

import me.jameschan.hole.common.StatusCode;
import me.jameschan.hole.command.Command;
import me.jameschan.hole.command.CommandTemplate;
import me.jameschan.hole.command.OptionTemplate;
import me.jameschan.hole.handler.Handler;
import me.jameschan.hole.common.Bundle;

public class DefaultHandler extends Handler {
    public DefaultHandler() {
        super(new CommandTemplate(0) {{
            addOptionTemplate(new OptionTemplate("version"));
            addOptionTemplate(new OptionTemplate("help"));
        }});
    }

    @Override
    public void handle(final Command command, final Bundle bundle) {
        bundle.statusCode = StatusCode.SUCCESS;

        final var versionOption = command.getOption("version");
        if (versionOption != null) {
            bundle.buffer.append("Hole v1.0.0");
            return;
        }

        final var helpOption = command.getOption("help");
        if (helpOption != null) {
            bundle.buffer.append("Manual of using Hole: ");
        }
    }
}
