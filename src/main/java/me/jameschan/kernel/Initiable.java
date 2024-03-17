package me.jameschan.kernel;

/**
 * Interface to represent initiable objects.
 * <p>
 * This interface defines a common protocol for objects that require explicit initialization before
 * they are used. Initialization may involve setting up initial states, allocating resources, or
 * performing any start-up procedures necessary for the object to function correctly.
 */
public interface Initiable {
    /**
     * Initializes the object. Implementations of this method should include all necessary start-up
     * logic to prepare the object for use. This might include allocating resources, initializing
     * internal state variables, or performing any preliminary checks or configurations required.
     * <p>
     * It is assumed that this method will be called before the object is used, and implementations
     * should ensure that the object is ready to perform its intended functions after `initial` is
     * called. Depending on the object's design, it might also be prudent to guard against multiple
     * initializations, either by ignoring subsequent calls or by resetting the object to its
     * initial state before reinitializing.
     */
    void init();
}