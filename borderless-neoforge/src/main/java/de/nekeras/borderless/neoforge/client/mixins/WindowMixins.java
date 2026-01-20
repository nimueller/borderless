package de.nekeras.borderless.neoforge.client.mixins;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.neoforge.client.BorderlessWindowClient;
import lombok.extern.slf4j.Slf4j;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Slf4j
@Mixin(Window.class)
public class WindowMixins {

    @Inject(method = "setMode", at = @At("TAIL"))
    private void setMode(CallbackInfo info) {
        BorderlessWindowClient.getDisplayModeHolder().setFullscreenDisplayModeFromConfig();
    }

}
