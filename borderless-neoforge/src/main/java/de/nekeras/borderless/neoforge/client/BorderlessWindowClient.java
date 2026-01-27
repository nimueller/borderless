package de.nekeras.borderless.neoforge.client;

import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.neoforge.client.provider.NeoForgeConfigProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BorderlessWindowClient {

    @Getter
    private static final BorderlessWindowClient instance = new BorderlessWindowClient();

    private final ConfigProvider configProvider = new NeoForgeConfigProvider();
    @Getter
    private final FullscreenDisplayModeHolder displayModeHolder = new FullscreenDisplayModeHolder(configProvider);

    private BorderlessWindowClient() {
    }

}
