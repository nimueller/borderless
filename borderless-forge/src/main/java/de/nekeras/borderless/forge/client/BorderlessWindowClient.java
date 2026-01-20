package de.nekeras.borderless.forge.client;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.common.FullscreenDisplayModeHolder;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.common.spi.MinecraftWindow;
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

    private static final Window unwrappedWindow = Minecraft.getInstance().getWindow();
    private static final MinecraftWindow window = new ForgeWindow(unwrappedWindow);
    private static final ConfigProvider configProvider = new ForgeConfigProvider();
    @Getter
    private static final FullscreenDisplayModeHolder displayModeHolder = new FullscreenDisplayModeHolder(configProvider,
        window);

    private BorderlessWindowClient() {
    }

}
