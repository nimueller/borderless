package de.nekeras.borderless.neoforge.client;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.common.spi.MinecraftWindow;
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
    @Getter
    private static boolean initialized = false;

    private BorderlessWindowClient() {
    }

    /**
     * Initializes the Minecraft environment to make it compatible with this mod.
     */
    public static void initMinecraft() {
        log.info("Overwrite finished");
        displayModeHolder.setFullscreenDisplayModeFromConfig();

        initialized = true;
    }

}
