package de.nekeras.borderless.neoforge.client;

import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.neoforge.client.provider.NeoForgeConfigProvider;
import de.nekeras.borderless.neoforge.client.provider.NeoForgeWindow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.Minecraft;

@Slf4j
public final class BorderlessWindowClient {

    @Getter
    private static final BorderlessWindowClient instance = new BorderlessWindowClient();

    private final ConfigProvider configProvider = new NeoForgeConfigProvider();
    @Getter
    private final FullscreenDisplayModeHolder displayModeHolder = new FullscreenDisplayModeHolder(configProvider);
    @Getter
    private boolean initialized = false;

    private BorderlessWindowClient() {
    }

    public void initMinecraft() {
        if (initialized) {
            log.debug("Borderless Window already initialized");
            return;
        }

        log.info("Initializing Borderless Window");
        var window = Minecraft.getInstance().getWindow();
        displayModeHolder.setFullscreenDisplayModeFromConfig(new NeoForgeWindow(window));
        initialized = true;
        log.info("Borderless Window initialized");
    }

}
