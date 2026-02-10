package de.nekeras.borderless.neoforge;

import de.nekeras.borderless.neoforge.client.BorderlessConfigScreenFactory;
import de.nekeras.borderless.neoforge.client.config.Config;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * The main Forge mod class.
 */
@Mod(value = BorderlessWindow.MOD_ID, dist = Dist.CLIENT)
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

        container.getEventBus().addListener(BorderlessWindow::onClientInit);

        Supplier<IConfigScreenFactory> screenFactory = BorderlessConfigScreenFactory::new;
        container.registerExtensionPoint(IConfigScreenFactory.class, screenFactory);
    }

    @SubscribeEvent
    public static void onClientInit(FMLClientSetupEvent event) {
        log.debug("ClientSetupEvent received, enqueuing initialization work");

        event.enqueueWork(() -> {
            log.info("Initializing Borderless Window Client");
            de.nekeras.borderless.neoforge.client.BorderlessWindowClient.getInstance().initMinecraft();
        });
    }

}
