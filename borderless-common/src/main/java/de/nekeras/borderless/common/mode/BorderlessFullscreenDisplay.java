package de.nekeras.borderless.common.mode;

import de.nekeras.borderless.common.glfw.GlfwUtils;
import de.nekeras.borderless.common.glfw.GlfwWindowAttribute;
import de.nekeras.borderless.common.spi.MinecraftMonitor;
import de.nekeras.borderless.common.spi.MinecraftVideoMode;
import de.nekeras.borderless.common.spi.MinecraftWindow;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

/**
 * Switches into a windowed mode and removes the borders of the window. After that, maximizes the
 * window on the current monitor as returned by {@link MinecraftWindow#findBestMonitor()}.
 */
@Slf4j
public class BorderlessFullscreenDisplay implements FullscreenDisplayMode {

    @Override
    public void apply(@Nonnull MinecraftWindow window) {
        FullscreenDisplayMode.super.apply(window);

        MinecraftMonitor monitor = GlfwUtils.tryGetMonitor(window).orElse(null);

        if (monitor == null) {
            log.error("Window's monitor could not be retrieved");
        } else {
            MinecraftVideoMode videoMode = monitor.getCurrentMode();
            String name = GlfwUtils.getMonitorName(monitor);
            int x = monitor.getX();
            int y = monitor.getY();
            int width = videoMode.getWidth();
            int height = videoMode.getHeight();

            log.info("Apply on monitor {} at ({}|{}) size ({} x {})", name, x, y, width, height);

            GlfwUtils.disableWindowAttribute(window, GlfwWindowAttribute.DECORATED);
            GLFW.glfwSetWindowMonitor(window.getHandle(), 0, x, y, width, height, GLFW.GLFW_DONT_CARE);
        }
    }

    @Override
    public void reset(@Nonnull MinecraftWindow window) {
        FullscreenDisplayMode.super.reset(window);

        if (window.isFullscreen()) {
            MinecraftMonitor monitor = GlfwUtils.tryGetMonitor(window).orElse(null);

            if (monitor == null) {
                log.error("Window's monitor could not be retrieved");
            } else {
                MinecraftVideoMode videoMode = monitor.getCurrentMode();
                String name = GlfwUtils.getMonitorName(monitor);
                int width = videoMode.getWidth();
                int height = videoMode.getHeight();

                log.info("Reset on monitor {} at size ({} x {})", name, width, height);

                GLFW.glfwSetWindowMonitor(window.getHandle(), monitor.getHandle(), 0, 0, width, height,
                    GLFW.GLFW_DONT_CARE);
            }
        }
    }

}
