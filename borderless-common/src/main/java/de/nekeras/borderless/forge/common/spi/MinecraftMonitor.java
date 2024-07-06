package de.nekeras.borderless.forge.common.spi;

public interface MinecraftMonitor {

    long getHandle();

    int getX();

    int getY();

    MinecraftVideoMode getCurrentMode();

}
