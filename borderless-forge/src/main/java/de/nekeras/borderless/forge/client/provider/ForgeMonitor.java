package de.nekeras.borderless.forge.client.provider;

import com.mojang.blaze3d.platform.Monitor;
import de.nekeras.borderless.forge.common.spi.MinecraftMonitor;
import de.nekeras.borderless.forge.common.spi.MinecraftVideoMode;

public record ForgeMonitor(Monitor monitor) implements MinecraftMonitor {

    @Override
    public long getHandle() {
        return monitor.getMonitor();
    }

    @Override
    public int getX() {
        return monitor.getX();
    }

    @Override
    public int getY() {
        return monitor.getY();
    }

    @Override
    public MinecraftVideoMode getCurrentMode() {
        return new ForgeVideoMode(monitor.getCurrentMode());
    }

}
