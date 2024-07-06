package de.nekeras.borderless.neoforge.client.provider;

import de.nekeras.borderless.common.mode.FullscreenDisplayMode;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.neoforge.client.config.Config;

public class NeoForgeConfigProvider implements ConfigProvider {

    @Override
    public FullscreenDisplayMode getFullscreenDisplayMode() {
        return Config.getFullscreenDisplayMode();
    }

}
