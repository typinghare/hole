package me.jameschan.hole.common;

/**
 * Bundle.
 */
public class Bundle {
    /**
     * The environment properties.
     */
    private final Env env;

    /**
     * The status code.
     */
    public StatusCode statusCode = StatusCode.NULL;

    /**
     * The buffer.
     */
    public final StringBuffer buffer = new StringBuffer();

    /**
     * Creates a Bundle instance.
     * @param env The environment properties.
     */
    public Bundle(final Env env) {
        this.env = env;
    }

    public Env getEnv() {
        return env;
    }

    /**
     * Creates a bundle.
     * @return a bundle.
     */
    public static Bundle create() {
        final var env = new Env(System.getProperty("user.dir"));
        return new Bundle(env);
    }
}
