package de.nekeras.borderless.common;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final String MOD_HINT = """
        This is a Minecraft mod.
        You cannot directly execute this JAR file.
        Instead, download and install the Forge Mod Loader and follow the mod installation instructions.
        """;

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println(MOD_HINT);
        } else {
            JOptionPane.showConfirmDialog(null, MOD_HINT, "Minecraft Mod", JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE);
        }

        System.exit(1);
    }

}
