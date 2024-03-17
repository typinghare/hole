package me.jameschan.kernel;

/**
 * An abstract class representing an object that can be initialized, destroyed, and used. It
 * implements the Initiable and Destroyable interfaces.
 */
public abstract class Usable implements Initiable, Destroyable {
    /**
     * Indicates if the object has been initialized.
     */
    private boolean initiated = false;

    /**
     * Indicates if the object has been destroyed.
     */
    private boolean destroyed = false;

    /**
     * Destroys the object.
     */
    @Override
    public void destroy() {
        this.destroyed = true;
    }

    /**
     * Initializes the object.
     */
    @Override
    public void init() {
        this.initiated = true;
    }

    /**
     * Checks if the object is initialized and initializes it if necessary.
     * @param autoInit If true, initializes the object if it's not already initialized.
     * @return The Usable object.
     * @throws RuntimeException if the object has been destroyed or not initiated.
     */
    public Usable useThis(boolean autoInit) {
        if (destroyed) {
            throw new RuntimeException("Usable class has been destroyed: " + getClass().getName());
        }

        if (!initiated && autoInit) {
            this.init();
        }

        if (!initiated) {
            throw new RuntimeException("Usable class has not been initiated: " + getClass().getName());
        }

        return this;
    }

    /**
     * Checks if the object is initialized and initializes it if necessary.
     * @return The Usable object.
     * @throws RuntimeException if the object has been destroyed or not initiated.
     */
    public Usable useThis() {
        return useThis(true);
    }
}
