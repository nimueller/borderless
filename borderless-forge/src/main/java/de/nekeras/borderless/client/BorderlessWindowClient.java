package de.nekeras.borderless.client;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.client.listener.SizeChangedWindowEventHandler;
import de.nekeras.borderless.client.provider.ForgeConfigProvider;
import de.nekeras.borderless.client.provider.ForgeWindow;
import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.common.spi.MinecraftWindow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Slf4j
@OnlyIn(Dist.CLIENT)
public final class BorderlessWindowClient {

    private static final Window unwrappedWindow = Minecraft.getInstance().getWindow();
    private static final MinecraftWindow window = new ForgeWindow(unwrappedWindow);
    private static final ConfigProvider configProvider = new ForgeConfigProvider();
    @Getter
    private static final FullscreenDisplayModeHolder displayModeHolder = new FullscreenDisplayModeHolder(configProvider,
        window);

    private BorderlessWindowClient() {
    }

    /**
     * Initializes the Minecraft environment to make it compatible with this mod.
     */
    public static void initMinecraft() {
        log.info("Overwriting Minecraft WindowEventListener");
        ForgeReflectionUtils.updateWindowEventListener(unwrappedWindow,
            oldListener -> new SizeChangedWindowEventHandler(oldListener,
                displayModeHolder::setFullscreenDisplayModeFromConfig));

        log.info("Overwrite finished");
        displayModeHolder.setFullscreenDisplayModeFromConfig();
    }

}
