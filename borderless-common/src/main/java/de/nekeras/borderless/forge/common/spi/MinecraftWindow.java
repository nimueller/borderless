package de.nekeras.borderless.forge.common.spi;

import java.util.Optional;

public interface MinecraftWindow {

    long getHandle();

    MinecraftMonitor findBestMonitor();

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    boolean isFullscreen();

    Optional<MinecraftVideoMode> getPreferredFullscreenVideoMode();

}
