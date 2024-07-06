package de.nekeras.borderless.common.spi;

public interface MinecraftMonitor {

    long getHandle();

    int getX();

    int getY();

    MinecraftVideoMode getCurrentMode();

}
