package de.nekeras.borderless.neoforge;

import de.nekeras.borderless.neoforge.client.BorderlessConfigScreenFactory;
import de.nekeras.borderless.neoforge.client.BorderlessWindowClient;
import de.nekeras.borderless.neoforge.client.config.Config;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * The main Forge mod class.
 */
@Mod(BorderlessWindow.MOD_ID)
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class BorderlessWindow {

    /**
     * The mod id of this Forge mod.
     */
    public static final String MOD_ID = "borderlesswindow";

    private static final Logger log = LogManager.getLogger();

    @SuppressWarnings("java:S1118")
    public BorderlessWindow(ModContainer container) {
        log.info("Register client configuration");
        container.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);

        Supplier<IConfigScreenFactory> screenFactory = BorderlessConfigScreenFactory::new;
        container.registerExtensionPoint(IConfigScreenFactory.class, screenFactory);
    }

    @SubscribeEvent
    public static void onClientSetup(@Nonnull FMLClientSetupEvent event) {
        log.info("Enqueue initialization work to main thread");
        event.enqueueWork(BorderlessWindowClient::initMinecraft);
    }

}
