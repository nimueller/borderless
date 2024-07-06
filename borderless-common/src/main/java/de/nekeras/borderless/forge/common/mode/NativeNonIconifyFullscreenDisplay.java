package de.nekeras.borderless.forge.common.mode;

import de.nekeras.borderless.forge.common.glfw.GlfwUtils;
import de.nekeras.borderless.forge.common.glfw.GlfwWindowAttribute;
import de.nekeras.borderless.forge.common.spi.MinecraftWindow;

import javax.annotation.Nonnull;

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
public class NativeNonIconifyFullscreenDisplay implements FullscreenDisplayMode {

    @Override
    public void apply(@Nonnull MinecraftWindow window) {
        FullscreenDisplayMode.super.apply(window);

        GlfwUtils.disableWindowAttribute(window, GlfwWindowAttribute.AUTO_ICONIFY);
    }

}
