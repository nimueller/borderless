package de.nekeras.borderless.common;

import de.nekeras.borderless.common.mode.FullscreenDisplayMode;
import de.nekeras.borderless.common.mode.NativeFullscreenDisplay;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.system.Platform;

import javax.annotation.Nonnull;

/**
 * The desktop environment we are running on, retrievable by {@link #get()}.
 */
@Slf4j
public enum DesktopEnvironment {

    /**
     * The Windows desktop environment.
     */
    WINDOWS(FullscreenDisplayMode.BORDERLESS),

    /**
     * X11 window system used by Linux distributions.
     */
    X11(FullscreenDisplayMode.NATIVE_NON_ICONIFY),

    /**
     * Wayland window server used by Linux distributions.
     */
    WAYLAND(FullscreenDisplayMode.NATIVE_NON_ICONIFY),

    /**
     * An unknown desktop environment, this should always use {@link NativeFullscreenDisplay}.
     */
    GENERIC(FullscreenDisplayMode.NATIVE);


    private static final String LINUX_WINDOW_SYSTEM_VARIABLE = "XDG_SESSION_TYPE";
    private static final String X11_NAME = "x11";
    private static final String WAYLAND_NAME = "wayland";
    private static final DesktopEnvironment CURRENT;

    private final FullscreenDisplayMode bestFullscreenDisplayMode;

    DesktopEnvironment(@Nonnull FullscreenDisplayMode bestFullscreenDisplayMode) {
        this.bestFullscreenDisplayMode = bestFullscreenDisplayMode;
    }

    /**
     * The best {@link FullscreenDisplayMode} for this desktop environment.
     *
     * @return The best mode
     */
    @Nonnull
    public FullscreenDisplayMode getBestFullscreenDisplayMode() {
        return bestFullscreenDisplayMode;
    }

    static {
        log.info("Determining desktop environment");

        DesktopEnvironment current = null;

        switch (Platform.get()) {
            case WINDOWS -> current = WINDOWS;
            case LINUX -> {
                String result = System.getenv(LINUX_WINDOW_SYSTEM_VARIABLE);
                if (X11_NAME.equalsIgnoreCase(result)) {
                    current = X11;
                } else if (WAYLAND_NAME.equalsIgnoreCase(result)) {
                    current = WAYLAND;
                } else {
                    current = GENERIC;
                    log.warn("Unknown window system: {}", result);
                }
            }
            case MACOSX -> current = GENERIC;
        }

        CURRENT = current;
        log.info("Found desktop environment {}", CURRENT);
    }

    /**
     * The current desktop environment Minecraft is running on.
     *
     * @return The desktop environment
     */
    @Nonnull
    public static DesktopEnvironment get() {
        return CURRENT;
    }

}
