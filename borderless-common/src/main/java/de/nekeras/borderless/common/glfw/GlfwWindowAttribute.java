package de.nekeras.borderless.common.glfw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.glfw.GLFW;

/**
 * Supported window attributes that are used by Borderless Window for the fullscreen display modes.
 */
@Getter
@RequiredArgsConstructor
public enum GlfwWindowAttribute {

    DECORATED(GLFW.GLFW_DECORATED, true),
    AUTO_ICONIFY(GLFW.GLFW_AUTO_ICONIFY, true);

    private final int bit;
    private final boolean enabledByDefault;

}
