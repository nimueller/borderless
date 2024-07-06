package de.nekeras.borderless.forge.client.provider;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.common.spi.MinecraftMonitor;
import de.nekeras.borderless.common.spi.MinecraftVideoMode;
import de.nekeras.borderless.common.spi.MinecraftWindow;

import java.util.Optional;

public record ForgeWindow(Window window) implements MinecraftWindow {

    @Override
    public long getHandle() {
        return window.getWindow();
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
