package de.nekeras.borderless.neoforge.client;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.forge.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.forge.common.spi.ConfigProvider;
import de.nekeras.borderless.forge.common.spi.MinecraftWindow;
import de.nekeras.borderless.neoforge.client.listener.SizeChangedWindowEventHandler;
import de.nekeras.borderless.neoforge.client.provider.NeoForgeConfigProvider;
import de.nekeras.borderless.neoforge.client.provider.NeoForgeWindow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.Minecraft;

@Slf4j
public final class BorderlessWindowClient {

    private static final Window unwrappedWindow = Minecraft.getInstance().getWindow();
    private static final MinecraftWindow window = new NeoForgeWindow(unwrappedWindow);
    private static final ConfigProvider configProvider = new NeoForgeConfigProvider();
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
