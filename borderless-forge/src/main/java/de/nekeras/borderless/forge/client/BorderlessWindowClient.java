package de.nekeras.borderless.forge.client;

import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.forge.client.provider.ForgeConfigProvider;
import de.nekeras.borderless.forge.client.provider.ForgeWindow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Slf4j
@OnlyIn(Dist.CLIENT)
public final class BorderlessWindowClient {

    @Getter
    private static final BorderlessWindowClient instance = new BorderlessWindowClient();

    private final ConfigProvider configProvider = new ForgeConfigProvider();
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
        displayModeHolder.setFullscreenDisplayModeFromConfig(new ForgeWindow(window));
        initialized = true;
        log.info("Borderless Window initialized");
    }

}
