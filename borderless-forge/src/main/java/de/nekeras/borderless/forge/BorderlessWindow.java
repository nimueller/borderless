package de.nekeras.borderless.forge;

import de.nekeras.borderless.forge.client.BorderlessWindowClient;
import de.nekeras.borderless.forge.client.config.Config;
import de.nekeras.borderless.forge.client.gui.ConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * The main Forge mod class.
 */
@Mod(BorderlessWindow.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class BorderlessWindow {

    /**
     * The mod id of this Forge mod.
     */
    public static final String MOD_ID = "borderlesswindow";

    private static final Logger log = LogManager.getLogger();

    @SuppressWarnings("java:S1118")
    public BorderlessWindow(FMLJavaModLoadingContext context) {
        log.info("Register client configuration");
        context.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);
        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
            new ConfigScreenHandler.ConfigScreenFactory((mc, modListScreen) -> new ConfigScreen(modListScreen)));
    }

    @SubscribeEvent
    public static void onClientSetup(@Nonnull FMLClientSetupEvent event) {
        log.info("Enqueue initialization work to main thread");
        event.enqueueWork(BorderlessWindowClient::initMinecraft);
    }

}
