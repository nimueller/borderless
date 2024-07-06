package de.nekeras.borderless.forge.client.provider;

import de.nekeras.borderless.forge.client.config.Config;
import de.nekeras.borderless.forge.common.mode.FullscreenDisplayMode;
import de.nekeras.borderless.forge.common.spi.ConfigProvider;

public class ForgeConfigProvider implements ConfigProvider {

    @Override
    public FullscreenDisplayMode getFullscreenDisplayMode() {
        return Config.getFullscreenDisplayMode();
    }

}
