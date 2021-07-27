package de.nekeras.borderless.client.fullscreen;

import de.nekeras.borderless.client.GlfwUtils;
import net.minecraft.client.MainWindow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * A fullscreen mode that can be applied for the Minecraft {@link MainWindow}. The {@link
 * #apply(MainWindow)} method
 * will be called every time the window enters fullscreen, the {@link #reset(MainWindow)} method
 * every time the window
 * leaves fullscreen.
 */
@OnlyIn(Dist.CLIENT)
public interface FullscreenDisplayMode {

    /**
     * Switches into a windowed mode and removes the borders of the window. After that, maximizes
     * the window on the current monitor as returned by {@link MainWindow#get()}.
     */
    FullscreenDisplayMode BORDERLESS = new BorderlessFullscreenDisplay();

    /**
     * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this
     * mode will not affect the {@link MainWindow}.
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
    default void apply(@Nonnull MainWindow window) {
        GlfwUtils.applyDefaultWindowAttributes(window);
    }

    /**
     * Resets this fullscreen mode on the supplied window, reverting any changes that were made in
     * {@link #apply(MainWindow)}.
     *
     * @param window The window
     */
    default void reset(@Nonnull MainWindow window) {
        GlfwUtils.applyDefaultWindowAttributes(window);
    }
}