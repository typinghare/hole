package me.jameschan.kernel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Central application class responsible for managing and providing access to various manager
 * objects. Utilizes a type-safe approach to store and retrieve manager instances based on their
 * class.
 */
public class App {
    /**
     * A map to store instances of managers by their class type. This allows for type-safe retrieval
     * of manager instances throughout the application, ensuring that only one instance of each
     * manager is created and reused, following the Singleton pattern for each type of manager.
     */
    protected final Map<Class<? extends Manager<?>>, Manager<?>> byClass = new HashMap<>();

    /**
     * Constructs an App instance and initializes all provided manager classes. Each manager is
     * instantiated with this App instance as a constructor argument. The provided manager classes
     * should all have a constructor that takes the given app class as parameter.
     * @param appClass         The class of the app.
     * @param managerClassList List of manager classes to be instantiated and stored.
     * @throws IllegalArgumentException if any manager could not be instantiated.
     */
    public App(
        final Class<? extends App> appClass,
        final List<Class<? extends Manager<?>>> managerClassList
    ) {
        for (final var managerClass : managerClassList) {
            try {
                final var manager = managerClass
                    .getDeclaredConstructor(appClass)
                    .newInstance(this);
                byClass.put(managerClass, manager);
            } catch (final Exception e) {
                final var message = String.format(
                    "Could not instantiate manager of class: %s",
                    managerClass.getSimpleName()
                );
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * Constructs an App instance and initializes all provided manager classes. Each manager is
     * instantiated with this App instance as a constructor argument.
     * @param managerClassList List of manager classes to be instantiated and stored.
     * @throws IllegalArgumentException if any manager could not be instantiated.
     */
    public App(final List<Class<? extends Manager<?>>> managerClassList) {
        this(App.class, managerClassList);
    }

    /**
     * Retrieves an instance of the specified manager class.
     * @param managerClass The class of the manager to retrieve.
     * @return The manager instance of the specified class.
     * @throws IllegalArgumentException if the manager does not exist.
     */
    public <T extends Manager<?>> T use(final Class<T> managerClass) {
        return Optional.of(this.byClass.get(managerClass))
            .map(Usable::useThis)
            .map(managerClass::cast)
            .orElseThrow(() -> {
                final var message = String.format(
                    "Manager does not exist: %s",
                    managerClass.getSimpleName()
                );
                return new IllegalArgumentException(message);
            });
    }
}
