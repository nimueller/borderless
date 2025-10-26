package de.nekeras.borderless.common.reflection;

import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Minecraft native code reflection util for easier access of some fields and methods.
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * Makes a field of type <code>T</code> in object <code>thisRef</code> accessible through reflection.
     *
     * @param inClass   The object to lookup for the field
     * @param fieldType The field to access
     * @throws IllegalStateException If there is no such field of type <code>F</code>
     */
    @SuppressWarnings("SameParameterValue") // might be needed in the future
    @Nonnull
    public static <C, F> AccessibleFieldDelegate<C, F> makeFieldAccessible(@Nonnull Class<C> inClass,
        @Nonnull Class<F> fieldType) {
        try {
            return new AccessibleFieldDelegate<>(inClass, fieldType);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(String.format("Expected field of type %s in class %s",
                inClass.getName(), fieldType.getName()), e);
        }
    }

    /**
     * Makes a field of type <code>F</code> in class <code>C</code> accessible through reflection by field name.
     *
     * @param inClass   The class to lookup for the field
     * @param fieldName The name of the field to access
     * @param fieldType The type of the field to access
     * @param <C>       The type of the class containing the field
     * @param <F>       The type of the field
     * @return An {@link AccessibleFieldDelegate} for the specified field
     * @throws IllegalStateException If there is no such field with the given name and type
     */
    @SuppressWarnings("SameParameterValue") // might be needed in the future
    @Nonnull
    public static <C, F> AccessibleFieldDelegate<C, F> makeFieldAccessible(@Nonnull Class<C> inClass,
        @Nonnull String fieldName, @Nonnull Class<F> fieldType) {
        try {
            return new AccessibleFieldDelegate<>(inClass, fieldName);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(String.format("Expected field '%s' of type %s in class %s",
                fieldName, fieldType.getName(), inClass.getName()), e);
        }
    }

    /**
     * Makes a field of type <code>T</code> in object <code>thisRef</code> accessible through reflection.
     *
     * @param inClass         The object to lookup for the field
     * @param fieldType       The field to access
     * @param defaultSupplier The supplier which is called in when retrieving the value if the field was not found
     */
    @SuppressWarnings("SameParameterValue") // might be needed in the future
    @Nonnull
    public static <C, F> AccessibleFieldDelegate<C, F> tryMakeFieldAccessible(@Nonnull Class<C> inClass,
        @Nonnull Class<F> fieldType, @Nonnull Function<C, F> defaultSupplier) {
        return new AccessibleFieldDelegate<>(inClass, fieldType, defaultSupplier);
    }

    /**
     * Checks if this method was called by either the {@link GLFWFramebufferSizeCallbackI} or
     * {@link GLFWWindowSizeCallbackI}.
     *
     * @return <code>true</code> if called by a GLFW callback, otherwise <code>false</code>
     */
    public static boolean isCalledByGlfwCallback() {
        return Arrays.stream(Thread.currentThread().getStackTrace()).anyMatch(e ->
            e.getClassName().equals(GLFWFramebufferSizeCallbackI.class.getName())
                || e.getClassName().equals(GLFWWindowSizeCallbackI.class.getName()));
    }

}
