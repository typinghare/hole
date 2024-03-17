package me.jameschan.hole;

import me.jameschan.hole.handler.HandlerManager;
import me.jameschan.hole.extend.HoleApp;

import java.util.Arrays;
import java.util.List;

public class Bootstrap {
    public static void main(final String[] args) {
        final var holeApp = new HoleApp();
        final var executorManager = holeApp.use(HandlerManager.class);
        executorManager.executeRawArgs(List.of(args));
    }
}
