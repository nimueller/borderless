package de.nekeras.borderless.neoforge.client;

import de.nekeras.borderless.neoforge.client.gui.ConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import javax.annotation.Nonnull;

public class BorderlessConfigScreenFactory implements IConfigScreenFactory {

    @Nonnull
    @Override
    public Screen createScreen(@Nonnull ModContainer modContainer, @Nonnull Screen screen) {
        return new ConfigScreen(screen);
    }

}
