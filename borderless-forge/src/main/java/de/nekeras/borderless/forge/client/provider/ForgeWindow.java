package de.nekeras.borderless.forge.client.provider;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.common.reflection.AccessibleFieldDelegate;
import de.nekeras.borderless.common.reflection.ReflectionUtils;
import de.nekeras.borderless.common.spi.MinecraftMonitor;
import de.nekeras.borderless.common.spi.MinecraftVideoMode;
import de.nekeras.borderless.common.spi.MinecraftWindow;

import java.util.Optional;

public record ForgeWindow(Window window) implements MinecraftWindow {

    private static final AccessibleFieldDelegate<Window, Long> HANDLE =
        ReflectionUtils.makeFieldAccessible(Window.class, Long.TYPE);

    @Override
    public long getHandle() {
        Long boxedValue = HANDLE.getValue(window);

        if (boxedValue == null) {
            throw new IllegalStateException("Window handle is null");
        }

        return boxedValue;
    }

    @Override
    public MinecraftMonitor findBestMonitor() {
        return new ForgeMonitor(window.findBestMonitor());
    }

    @Override
    public int getX() {
        return window.getX();
    }

    @Override
    public int getY() {
        return window.getY();
    }

    @Override
    public int getWidth() {
        return window.getWidth();
    }

    @Override
    public int getHeight() {
        return window.getHeight();
    }

    @Override
    public boolean isFullscreen() {
        return window.isFullscreen();
    }

    @Override
    public Optional<MinecraftVideoMode> getPreferredFullscreenVideoMode() {
        return window.getPreferredFullscreenVideoMode().map(ForgeVideoMode::new);
    }

}
