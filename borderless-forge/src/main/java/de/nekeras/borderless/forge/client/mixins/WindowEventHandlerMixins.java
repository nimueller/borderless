package de.nekeras.borderless.forge.client.mixins;

import de.nekeras.borderless.forge.client.BorderlessWindowClient;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Slf4j
@Mixin(Minecraft.class)
public class WindowEventHandlerMixins {

    @Inject(method = "resizeDisplay", at = @At("TAIL"))
    private void resizeDisplay(CallbackInfo ignored) {
        if (!BorderlessWindowClient.isInitialized()) {
            return;
        }

        var displayModeHolder = BorderlessWindowClient.getDisplayModeHolder();
        displayModeHolder.setFullscreenDisplayModeFromConfig();
    }

}
