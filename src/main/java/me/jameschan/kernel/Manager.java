package me.jameschan.kernel;

/**
 * Represents a manager class responsible for managing specific functionalities or resources within
 * the application context. Managers extend the AppBased class, which allows them to directly
 * interact with the broader application's functionality, resources, and lifecycle management
 * practices.
 * <p>
 * Managers serve as intermediaries between components and the application, encapsulating logic
 * related to the management of resources or services. By extending the Manager class, developers
 * can create specialized manager instances tailored to specific functionalities or subsystems
 * within the application.
 * @param <A> The type of the application instance this manager is associated with.
 */
public abstract class Manager<A extends App> extends AppBased<A> {
    /**
     * Constructs a Manager object, establishing a foundational link to the application context.
     * @param app The application instance this object is based upon. This parameter ensures that
     *            the manager has direct access to the application it belongs to, facilitating
     *            interactions with global states and services provided by the application.
     */
    public Manager(final A app) {
        super(app);
    }
}