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
 * The same as {@link NativeFullscreenDisplay}, but switches to normal windowed mode on focus loss of
 * the window.
 */
@Slf4j
public class NativeWindowedFullscreenDisplay implements FullscreenDisplayMode {

    @Override
    public void apply(@Nonnull MinecraftWindow window) {
        FullscreenDisplayMode.super.apply(window);

        GlfwUtils.disableWindowAttribute(window, GlfwWindowAttribute.AUTO_ICONIFY);
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowFocusCallback(window.getHandle(), (win, focused) -> {
            if (focused) {
                onFocusGained(window);
            } else {
                onFocusLost(window);
            }
        });
    }

    @Override
    public void reset(@Nonnull MinecraftWindow window) {
        FullscreenDisplayMode.super.reset(window);

        GLFW.glfwSetWindowFocusCallback(window.getHandle(), null);
    }

    private void onFocusGained(@Nonnull MinecraftWindow window) {
        MinecraftMonitor monitor = GlfwUtils.tryGetMonitor(window).orElse(null);

        if (monitor == null) {
            log.error("Could not find monitor for window");
        } else {
            MinecraftVideoMode videoMode = window.getPreferredFullscreenVideoMode().orElseGet(monitor::getCurrentMode);

            GLFW.glfwSetWindowMonitor(window.getHandle(), monitor.getHandle(), 0, 0,
                videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate());
        }
    }

    private void onFocusLost(@Nonnull MinecraftWindow window) {
        GLFW.glfwSetWindowMonitor(window.getHandle(), 0,
            window.getX(), window.getY(),
            window.getWidth(), window.getHeight(), GLFW.GLFW_DONT_CARE);
    }

}
