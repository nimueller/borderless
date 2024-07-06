package de.nekeras.borderless.forge.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import de.nekeras.borderless.forge.common.reflection.AccessibleFieldDelegate;
import de.nekeras.borderless.forge.common.reflection.ReflectionUtils;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * Minecraft native code reflection util for easier access of some fields and methods.
 */
@OnlyIn(Dist.CLIENT)
public final class ForgeReflectionUtils {

    private static final AccessibleFieldDelegate<Window, WindowEventHandler> windowEventListenerAccessor =
        ReflectionUtils.makeFieldAccessible(Window.class, WindowEventHandler.class);
    private static final AccessibleFieldDelegate<VideoSettingsScreen, OptionsList> optionsListAccessor =
        ReflectionUtils.tryMakeFieldAccessible(VideoSettingsScreen.class, OptionsList.class, thisRef -> null);

    private ForgeReflectionUtils() {
    }

    /**
     * Updates the {@link WindowEventHandler} in the {@link Window} instance with a custom
     * window event listener.
     *
     * @param window         The window to update the listener for
     * @param updateSupplier A function that accepts the original value of the
     *                       {@link WindowEventHandler} field in the {@link Window} instance
     *                       and returns a new value that should be assigned
     */
    public static void updateWindowEventListener(@Nonnull Window window,
        @Nonnull Function<WindowEventHandler, WindowEventHandler> updateSupplier) {
        WindowEventHandler oldListener = windowEventListenerAccessor.getValue(window);
        WindowEventHandler newListener = updateSupplier.apply(oldListener);
        windowEventListenerAccessor.setValue(window, newListener);
    }

}
