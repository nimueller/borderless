package de.nekeras.borderless.forge;

import de.nekeras.borderless.forge.client.config.Config;
import de.nekeras.borderless.forge.client.gui.ConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main Forge mod class.
 */
@Mod(BorderlessWindow.MOD_ID)
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

        if (FMLEnvironment.dist.isClient()) {
            log.info("We are on the client, doing some client specific stuff");
            log.info("Registering Borderless Window Config Screen");
            context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((mc, modListScreen) -> new ConfigScreen(modListScreen)));

            log.info("Initializing Borderless Window Client");
            de.nekeras.borderless.forge.client.BorderlessWindowClient.getInstance().initMinecraft();
        }
    }

}
