package de.nekeras.borderless.forge.client.config;

import de.nekeras.borderless.common.mode.FullscreenDisplayMode;
import lombok.Getter;
import lombok.NonNull;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings for a focus loss behavior that is applied on a fullscreen window. These setting will
 * only be applied to a {@link FullscreenDisplayMode#NATIVE native fullscreen window}.
 */
public enum FocusLossConfig {

    /**
     * Doesn't do anything when focus on a fullscreen window is lost, the window may be always on
     * top, depending on the operating system.
     */
    DO_NOTHING("Doesn't do anything when focus on a fullscreen window is lost, the window may be "
            + "always on top, depending on the operating system."),

    /**
     * Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the default
     * Minecraft behavior.
     */
    MINIMIZE(
            "Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the "
                    + "default Minecraft behaviour."),

    /**
     * Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window is
     * lost.
     */
    SWITCH_TO_WINDOWED(
            "Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window "
                    + "is lost."),

    ;

    public static final OptionInstance.CaptionBasedToString<FocusLossConfig> VALUE_STRINGIFIER
            = new ValueStringifier();

    private static final String BASE_KEY = "borderless.config.focus_loss.%s";

    @Getter
    private final String comment;
    private final String translationKey;

    FocusLossConfig(@Nonnull String comment) {
        this.comment = comment;
        this.translationKey = String.format(BASE_KEY, name().toLowerCase());
    }

    @Override
    public String toString() {
        return translationKey;
    }

    private static class ValueStringifier implements OptionInstance.CaptionBasedToString<FocusLossConfig> {

        @NonNull
        @Override
        public Component toString(@NonNull Component pCaption, @Nullable FocusLossConfig pValue) {
            if (pValue == null) {
                return Component.empty();
            }

            return Component.translatable(pValue.translationKey);
        }
    }
}
