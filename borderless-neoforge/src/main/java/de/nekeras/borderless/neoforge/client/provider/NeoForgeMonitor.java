package de.nekeras.borderless.neoforge.client.provider;

import com.mojang.blaze3d.platform.Monitor;
import de.nekeras.borderless.forge.common.spi.MinecraftMonitor;
import de.nekeras.borderless.forge.common.spi.MinecraftVideoMode;

public record NeoForgeMonitor(Monitor monitor) implements MinecraftMonitor {

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
        return new NeoForgeVideoMode(monitor.getCurrentMode());
    }

}
