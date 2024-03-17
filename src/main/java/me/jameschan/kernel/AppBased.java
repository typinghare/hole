package me.jameschan.kernel;

/**
 * Represents a foundational class for objects that are inherently tied to an application context.
 * This abstract class serves as a base for any component that needs to initialize and clean up
 * resources in alignment with an application's lifecycle. It implements both the Initiable and
 * Destroyable interfaces, enforcing a contract for initialization and destruction processes within
 * the application framework.
 * <p>
 * By integrating with a specific instance of an App object, classes extending AppBased can directly
 * interact with the broader application's functionality, resources, and lifecycle management
 * practices. This design promotes a structured and consistent approach to resource management and
 * component lifecycle within the application.
 */
public abstract class AppBased<A extends App> extends Usable {
    /**
     * A protected reference to the App instance this object is associated with. This association
     * allows derived classes to interact with and manipulate application-level resources or
     * services.
     */
    protected final A app;

    /**
     * Constructs an AppBased object, establishing a foundational link to the application context.
     * @param app The application instance this object is based upon. This parameter ensures that
     *            the object has direct access to the application it belongs to, facilitating
     *            interactions with global states and services provided by the application.
     */
    public AppBased(final A app) {
        this.app = app;
    }

    /**
     * Retrieves an instance of the specified manager class.
     * @param managerClass The class of the manager to retrieve.
     * @return The manager instance of the specified class.
     * @throws IllegalArgumentException if the manager does not exist.
     */
    public <T extends Manager<?>> T use(final Class<T> managerClass) {
        return app.use(managerClass);
    }
}