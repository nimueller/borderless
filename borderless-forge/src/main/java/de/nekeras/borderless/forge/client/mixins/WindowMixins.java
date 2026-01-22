package de.nekeras.borderless.forge.client.mixins;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.forge.client.BorderlessWindowClient;
import de.nekeras.borderless.forge.client.provider.ForgeWindow;
import lombok.extern.slf4j.Slf4j;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.nekeras.borderless.common.MixinUtils.thisRef;

@Slf4j
@Mixin(Window.class)
public class WindowMixins {

    @Inject(method = "setMode", at = @At("TAIL"))
    private void setMode(CallbackInfo info) {
        Window window = thisRef(this);
        var borderlessWindowClient = BorderlessWindowClient.getInstance();

        if (!borderlessWindowClient.isInitialized()) {
            // Client is not initialized yet, wait until it is
            return;
        }

        var displayModeHolder = borderlessWindowClient.getDisplayModeHolder();
        displayModeHolder.setFullscreenDisplayModeFromConfig(new ForgeWindow(window));
    }

}
