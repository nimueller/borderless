package de.nekeras.borderless.neoforge.client.config;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An enum storing all supported fullscreen modes that can be configured in the {@link Config}.
 */
public enum FullscreenModeConfig {

    /**
     * The best suitable fullscreen mode for the current operating system.
     */
    BEST("The best suitable fullscreen mode for the current operating system."),

    /**
     * A borderless fullscreen which sets the width and height of the window to the monitor's video
     * mode and removing
     * window borders.
     */
    BORDERLESS("A borderless fullscreen which sets the width and height of the window to the "
            + "monitor's video mode and removing window borders."),

    /**
     * A native fullscreen which changes the monitor's window mode in order to apply the fullscreen.
     * Focus loss behavior can be manually configured using {@link FocusLossConfig}.
     */
    NATIVE("A native fullscreen which changes the monitor's window mode in order to apply the "
            + "fullscreen. Focus loss behaviour can be manually configured using the 'focusLoss' "
            + "option."),

    ;

    public static final OptionInstance.CaptionBasedToString<FullscreenModeConfig> VALUE_STRINGIFIER
            = new ValueStringifier();

    private static final String BASE_KEY = "borderless.config.fullscreen_mode.%s";

    @Getter
    private final String comment;
    private final String translationKey;

    FullscreenModeConfig(@Nonnull String comment) {
        this.comment = comment;
        this.translationKey = String.format(BASE_KEY, name().toLowerCase());
    }

    @Override
    public String toString() {
        return translationKey;
    }

    private static class ValueStringifier implements OptionInstance.CaptionBasedToString<FullscreenModeConfig> {

        @NonNull
        @Override
        public Component toString(@NonNull Component pCaption, @Nullable FullscreenModeConfig pValue) {
            if (pValue == null) {
                return Component.empty();
            }

            return Component.translatable(pValue.translationKey);
        }
    }
}
