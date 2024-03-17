package me.jameschan.kernel;

/**
 * Interface to represent destroyable objects.
 * <p>
 * This interface defines a common protocol for objects that can be explicitly destroyed, releasing
 * any resources or performing clean-up tasks before the object is discarded.
 */
public interface Destroyable {
    /**
     * Destroys this object. Implementations of this method should ensure that all resources
     * allocated by this object are released, and any necessary clean-up operations are performed.
     * Once this method is called, the object may be in a state that no longer allows it to function
     * correctly, hence it should not be used after calling this method.
     */
    void destroy();
}