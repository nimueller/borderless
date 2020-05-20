package de.nekeras.borderless;

import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.ConfigScreen;
import de.nekeras.borderless.fullscreen.FullscreenMode;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * The main Forge mod class.
 */
@Mod(Borderless.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class Borderless {

    /**
     * The mod id of this Forge mod.
     */
    public static final String MOD_ID = "borderless";

    private static final Logger LOG = LogManager.getLogger();
    private static FullscreenMode fullscreenMode;

    public Borderless() {
        ModLoadingContext context = ModLoadingContext.get();

        // Client dist only, make sure server is always compatible with this mod
        context.registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(
            () -> FMLNetworkConstants.IGNORESERVERONLY,
            (a, b) -> true));

        // Config registration
        context.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);
        context.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
            () -> (mc, modListScreen) -> new ConfigScreen(modListScreen));
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onClientSetup(@Nullable FMLClientSetupEvent event) {
        fullscreenMode = FullscreenMode.fromConfig();

        LOG.info("Enqueue WindowEventListener update to main thread");

        DeferredWorkQueue.runLater(() -> {
            LOG.info("Overwriting Minecraft WindowEventListener");
            Minecraft minecraft = Minecraft.getInstance();
            MainWindow window = minecraft.getMainWindow();
            ReflectionUtil.updateWindowEventListener(window, FullscreenWindowEventListener::new);
            LOG.info("Overwrite finished");

            forceFullscreenModeUpdate();
        });
    }

    @SubscribeEvent
    public void onConfigReload(ModConfig.Reloading event) {
        setFullscreenMode(FullscreenMode.fromConfig());
    }

    /**
     * Checks whether the {@link MainWindow} is currently in native fullscreen. This
     * may return different results than {@link MainWindow#isFullscreen()}. The window is considered
     * in native fullscreen if {@link GLFW#glfwGetWindowMonitor(long)} returns non-null and
     * {@link MainWindow#isFullscreen()} returns <code>true</code>.
     *
     * @return <code>true</code> if the window is currently in native fullscreen, otherwise
     * <code>false</code>
     */
    public static boolean isInNativeFullscreen(@Nonnull MainWindow window) {
        return window.isFullscreen() && GLFW.glfwGetWindowMonitor(window.getHandle()) != 0;
    }

    /**
     * The fullscreen mode that is applied instead of the native fullscreen once the user hits
     * F11 or switches to fullscreen in the video settings.
     *
     * @return The fullscreen mode
     */
    public static FullscreenMode getFullscreenMode() {
        return fullscreenMode;
    }

    /**
     * The fullscreen mode that is applied instead of the native fullscreen once the user hits
     * F11 or switches to fullscreen in the video settings.
     *
     * @param fullscreenMode
     *     The fullscreen mode
     */
    public static void setFullscreenMode(@Nonnull FullscreenMode fullscreenMode) {
        Objects.requireNonNull(fullscreenMode);

        Minecraft minecraft = Minecraft.getInstance();
        MainWindow window = minecraft.getMainWindow();

        LOG.info("Resetting fullscreen mode '{}' - Window fullscreen: {}; Native fullscreen: {}",
            Borderless.fullscreenMode.getClass().getName(),
            window.isFullscreen(),
            isInNativeFullscreen(window));

        if (Borderless.fullscreenMode != null && !Borderless.fullscreenMode.equals(fullscreenMode)) {
            Borderless.fullscreenMode.reset(window);
        }

        if (window.isFullscreen()) {
            LOG.info("Applying fullscreen mode '{}'", fullscreenMode.getClass().getName());
            Borderless.fullscreenMode = fullscreenMode;
            Borderless.fullscreenMode.apply(window);
        }
    }

    /**
     * Triggers an update for current {@link FullscreenMode}.
     */
    public static void forceFullscreenModeUpdate() {
        setFullscreenMode(getFullscreenMode());
    }

}
