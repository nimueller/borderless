package de.nekeras.borderless.common.mode;

import de.nekeras.borderless.common.glfw.GlfwUtils;
import de.nekeras.borderless.common.spi.MinecraftWindow;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * A fullscreen mode that can be applied for the Minecraft {@link MinecraftWindow}. The {@link
 * #apply(MinecraftWindow)} method
 * will be called every time the window enters fullscreen, the {@link #reset(MinecraftWindow)} method
 * every time the window
 * leaves fullscreen.
 */
public interface FullscreenDisplayMode {

    /**
     * Switches into a windowed mode and removes the borders of the window. After that, maximizes
     * the window on the current monitor as returned by {@link MinecraftWindow#findBestMonitor()}.
     */
    FullscreenDisplayMode BORDERLESS = new BorderlessFullscreenDisplay();

    /**
     * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this
     * mode will not affect the {@link Window}.
     */
    FullscreenDisplayMode NATIVE = new NativeFullscreenDisplay();

    /**
     * The native fullscreen mode, but without automatic iconify on focus loss of the window.
     */
    FullscreenDisplayMode NATIVE_NON_ICONIFY = new NativeNonIconifyFullscreenDisplay();

    /**
     * The same as {@link NativeFullscreenDisplay}, but switches to normal windowed mode on focus loss of
     * the window.
     */
    FullscreenDisplayMode NATIVE_SWITCH_TO_WINDOWED = new NativeWindowedFullscreenDisplay();

    /**
     * Applies this fullscreen mode on the supplied window.
     *
     * @param window The window
     */
    default void apply(@Nonnull MinecraftWindow window) {
        GlfwUtils.applyDefaultWindowAttributes(window);
    }

    /**
     * Resets this fullscreen mode on the supplied window, reverting any changes that were made in
     * {@link #apply(MinecraftWindow)}.
     *
     * @param window The window
     */
    default void reset(@Nonnull MinecraftWindow window) {
        GlfwUtils.applyDefaultWindowAttributes(window);
    }

}
