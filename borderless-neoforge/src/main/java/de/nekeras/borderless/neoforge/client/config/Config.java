package de.nekeras.borderless.neoforge.client.config;

import de.nekeras.borderless.common.DesktopEnvironment;
import de.nekeras.borderless.common.mode.FullscreenDisplayMode;
import net.neoforged.neoforge.common.ModConfigSpec;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class Config {

    private static final String GENERAL_PATH = "general";
    private static final String ENABLED_PATH = "enabled";
    private static final String FULLSCREEN_MODE_PATH = "fullscreenMode";
    private static final String FOCUS_LOSS_PATH = "focusLoss";
    private static final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

    /**
     * The final configuration specification.
     */
    public static final General GENERAL = new General(builder);
    public static final ModConfigSpec CONFIG_SPEC = builder.build();

    /**
     * The display mode as it is currently configured.
     *
     * @return The fullscreen display mode
     */
    @Nonnull
    public static FullscreenDisplayMode getFullscreenDisplayMode() {
        if (Boolean.TRUE.equals(GENERAL.enabled.get())) {
            return switch (GENERAL.fullscreenMode.get()) {
                case BEST -> DesktopEnvironment.get().getBestFullscreenDisplayMode();
                case BORDERLESS -> FullscreenDisplayMode.BORDERLESS;
                case NATIVE -> switch (GENERAL.focusLoss.get()) {
                    case MINIMIZE -> FullscreenDisplayMode.NATIVE;
                    case DO_NOTHING -> FullscreenDisplayMode.NATIVE_NON_ICONIFY;
                    case SWITCH_TO_WINDOWED -> FullscreenDisplayMode.NATIVE_SWITCH_TO_WINDOWED;
                };
            };
        }

        return FullscreenDisplayMode.NATIVE;
    }

    public static class General {

        /**
         * Whether the mod should be enabled.
         */
        public final ModConfigSpec.BooleanValue enabled;

        /**
         * The fullscreen mode to use, for more information see {@link FullscreenModeConfig}.
         *
         * @see FullscreenModeConfig
         */
        public final ModConfigSpec.EnumValue<FullscreenModeConfig> fullscreenMode;

        /**
         * The focus loss behaviour to use, for more information see {@link FocusLossConfig}.
         *
         * @see FocusLossConfig
         */
        public final ModConfigSpec.EnumValue<FocusLossConfig> focusLoss;

        public General(ModConfigSpec.Builder builder) {
            builder.push(GENERAL_PATH);

            enabled = builder.define(ENABLED_PATH, true);

            fullscreenMode = builder
                .comment(Arrays.stream(FullscreenModeConfig.values())
                    .map(mode -> String.format("%s - %s", mode.name(), mode.getComment()))
                    .toArray(String[]::new))
                .defineEnum(FULLSCREEN_MODE_PATH, FullscreenModeConfig.BEST);

            focusLoss = builder
                .comment(Arrays.stream(FocusLossConfig.values())
                    .map(mode -> String.format("%s - %s", mode.name(), mode.getComment()))
                    .toArray(String[]::new))
                .defineEnum(FOCUS_LOSS_PATH, FocusLossConfig.MINIMIZE);

            builder.pop();
        }

    }

}
