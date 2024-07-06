package de.nekeras.borderless.common;

import de.nekeras.borderless.common.glfw.GlfwUtils;
import de.nekeras.borderless.common.mode.FullscreenDisplayMode;
import de.nekeras.borderless.common.spi.ConfigProvider;
import de.nekeras.borderless.common.spi.MinecraftWindow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;

/**
 * Singleton class holding the current fullscreen display mode.
 */
@Slf4j
@RequiredArgsConstructor
public class FullscreenDisplayModeHolder {

    private final ConfigProvider configProvider;
    private final MinecraftWindow window;
    private FullscreenDisplayMode currentMode;

    /**
     * The fullscreen mode that is applied instead of the native fullscreen once the user hits
     * F11 or switches to fullscreen in the video settings.
     *
     * @param newMode The fullscreen mode
     */
    public void setFullscreenDisplayMode(@Nullable FullscreenDisplayMode newMode) {
        log.info("Detected fullscreen mode change from {} to {}", currentMode, newMode);

        if (currentMode != null) {
            currentMode.reset(window);
        }

        log.info("Refreshing {}", newMode);

        if (newMode != null) {
            if (window.isFullscreen()) {
                newMode.apply(window);
            } else {
                newMode.reset(window);
            }
        }

        currentMode = newMode;

        GlfwUtils.checkInputMode(window);
    }

    /**
     * Re-applies the current fullscreen display mode set in the {@link ConfigProvider}.
     */
    public void setFullscreenDisplayModeFromConfig() {
        FullscreenDisplayMode configMode = configProvider.getFullscreenDisplayMode();
        log.info("Refreshing fullscreen mode from config to {}", configMode);
        setFullscreenDisplayMode(configMode);
    }

}
