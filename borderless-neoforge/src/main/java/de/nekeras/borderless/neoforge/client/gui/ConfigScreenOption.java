package de.nekeras.borderless.neoforge.client.gui;

import com.mojang.serialization.Codec;
import de.nekeras.borderless.neoforge.client.config.Config;
import de.nekeras.borderless.neoforge.client.config.FocusLossConfig;
import de.nekeras.borderless.neoforge.client.config.FullscreenModeConfig;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public class ConfigScreenOption {

    private static final String ENABLED_KEY = "borderless.config.enabled";
    private static final String FULLSCREEN_MODE_KEY = "borderless.config.fullscreen_mode";
    private static final String FOCUS_LOSS_KEY = "borderless.config.focus_loss";
    private static final Component enabledTooltip = Component.translatable("borderless.config.enabled.tooltip");

    public static final OptionInstance<Boolean> enabled = OptionInstance.createBoolean(
        ENABLED_KEY,
        value -> Tooltip.create(enabledTooltip),
        Config.GENERAL.enabled.get(),
        Config.GENERAL.enabled::set
    );

    public static final OptionInstance<FullscreenModeConfig> fullscreenMode = new OptionInstance<>(
        FULLSCREEN_MODE_KEY,
        OptionInstance.noTooltip(),
        OptionInstance.forOptionEnum(),
        new OptionInstance.Enum<>(Arrays.asList(FullscreenModeConfig.values()),
            Codec.STRING.xmap(FullscreenModeConfig::valueOf, Enum::name)),
        Config.GENERAL.fullscreenMode.get(),
        Config.GENERAL.fullscreenMode::set
    );

    public static final OptionInstance<FocusLossConfig> focusLoss = new OptionInstance<>(
        FOCUS_LOSS_KEY,
        OptionInstance.noTooltip(),
        OptionInstance.forOptionEnum(),
        new OptionInstance.Enum<>(Arrays.asList(FocusLossConfig.values()),
            Codec.STRING.xmap(FocusLossConfig::valueOf, Enum::name)),
        Config.GENERAL.focusLoss.get(),
        Config.GENERAL.focusLoss::set
    );

}
