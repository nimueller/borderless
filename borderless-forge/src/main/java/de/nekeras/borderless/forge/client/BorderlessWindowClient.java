package de.nekeras.borderless.forge.client;

import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.forge.client.provider.ForgeConfigProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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

    private BorderlessWindowClient() {
    }

}
