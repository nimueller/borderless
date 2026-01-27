package de.nekeras.borderless.neoforge.client.mixins;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.neoforge.client.BorderlessWindowClient;
import de.nekeras.borderless.neoforge.client.provider.NeoForgeWindow;
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
        var displayModeHolder = BorderlessWindowClient.getInstance().getDisplayModeHolder();
        displayModeHolder.setFullscreenDisplayModeFromConfig(new NeoForgeWindow(window));
    }

}
