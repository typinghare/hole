package me.jameschan.hole.extend;

import me.jameschan.kernel.Manager;

public class HoleManager extends Manager<HoleApp> {
    /**
     * Constructs an AppBased object, establishing a foundational link to the application context.
     * @param app The application instance this object is based upon.
     */
    public HoleManager(final HoleApp app) {
        super(app);
    }
}