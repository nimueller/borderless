package de.nekeras.borderless.forge.common.glfw;

import de.nekeras.borderless.forge.common.spi.MinecraftMonitor;
import de.nekeras.borderless.forge.common.spi.MinecraftWindow;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Helper class for access for various GLFW functions with logging support.
 */
@Slf4j
public final class GlfwUtils {

    private GlfwUtils() {
    }

    /**
     * Retrieves the monitor's name from GLFW.
     *
     * @param monitor The monitor
     * @return The monitor's name
     */
    @Nonnull
    public static String getMonitorName(@Nonnull MinecraftMonitor monitor) {
        String name = GLFW.glfwGetMonitorName(monitor.getHandle());

        if (name == null) {
            log.warn("Could not retrieve monitor name for {}", monitor.getHandle());
            return "- ERROR -";
        } else {
            return name;
        }
    }

    /**
     * Tries to get the monitor for the window. If it could be found and is non-null, it will be returned. Otherwise, an
     * error is printed.
     *
     * @param window The window
     * @return The monitor wrapped in an {@link Optional}.
     */
    @Nonnull
    public static Optional<MinecraftMonitor> tryGetMonitor(@Nonnull MinecraftWindow window) {
        MinecraftMonitor monitor = window.findBestMonitor();

        if (monitor == null) {
            log.error("Window's current monitor could not be retrieved");
        }

        return Optional.ofNullable(monitor);
    }

    /**
     * Enables a single window attribute.
     *
     * @param window    The window
     * @param attribute The attribute
     */
    public static void enableWindowAttribute(@Nonnull MinecraftWindow window, @Nonnull GlfwWindowAttribute attribute) {
        log.info("Enable window attribute {}", attribute.name());
        GLFW.glfwSetWindowAttrib(window.getHandle(), attribute.getBit(), GLFW.GLFW_TRUE);
    }

    /**
     * Disables a single window attribute.
     *
     * @param window    The window
     * @param attribute The attribute
     */
    public static void disableWindowAttribute(@Nonnull MinecraftWindow window, @Nonnull GlfwWindowAttribute attribute) {
        log.info("Disable window attribute {}", attribute.name());
        GLFW.glfwSetWindowAttrib(window.getHandle(), attribute.getBit(), GLFW.GLFW_FALSE);
    }

    /**
     * Restores all default window attributes that are supported by the {@link GlfwWindowAttribute} enum.
     *
     * @param window The window
     */
    public static void applyDefaultWindowAttributes(@Nonnull MinecraftWindow window) {
        log.info("Resetting window attributes");

        for (GlfwWindowAttribute attribute : GlfwWindowAttribute.values()) {
            if (attribute.isEnabledByDefault()) {
                enableWindowAttribute(window, attribute);
            } else {
                disableWindowAttribute(window, attribute);
            }
        }

        log.info("Done resetting window attributes");
    }

    /**
     * Prints information of the current input mode for debugging purposes.
     *
     * @param window The window to check
     */
    public static void checkInputMode(@Nonnull MinecraftWindow window) {
        log.info("Checking window input mode");

        switch (GLFW.glfwGetInputMode(window.getHandle(), GLFW.GLFW_CURSOR)) {
            case GLFW.GLFW_CURSOR_NORMAL -> log.info("Detected normal cursor mode");
            case GLFW.GLFW_CURSOR_HIDDEN -> {
                log.info("Detected hidden cursor mode");
                GLFW.glfwSetInputMode(window.getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
            }
            case GLFW.GLFW_CURSOR_DISABLED -> log.info("Detected disabled cursor mode");
            default -> log.info("Unknown cursor mode");
        }

        log.info("Done checking window input mode");

        double[] xPos = new double[1];
        double[] yPos = new double[1];

        GLFW.glfwGetCursorPos(window.getHandle(), xPos, yPos);

        int x = (int) Math.floor(xPos[0]);
        int y = (int) Math.floor(yPos[0]);
        int winWidth = window.getWidth();
        int winHeight = window.getHeight();

        boolean inside = x >= 0 && x <= winWidth && y >= 0 && y <= winHeight;
        log.info("Cursor is at {}x{} (inside window: {})", x, y, inside);
        log.info("Done checking cursor position");
    }

}
