package de.nekeras.borderless.forge.client.provider;

import com.mojang.blaze3d.platform.VideoMode;
import de.nekeras.borderless.common.spi.MinecraftVideoMode;

public record ForgeVideoMode(VideoMode videoMode) implements MinecraftVideoMode {

    @Override
    public int getWidth() {
        return videoMode.getWidth();
    }

    @Override
    public int getHeight() {
        return videoMode.getHeight();
    }

    @Override
    public int getRefreshRate() {
        return videoMode.getRefreshRate();
    }

}
