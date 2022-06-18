package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import io.github.darkkronicle.darkkore.util.text.StyleFormatter;
import net.minecraft.client.MinecraftClient;

public class KeyToggleBoolean implements Runnable {

    private final BooleanOption option;

    public KeyToggleBoolean(BooleanOption config) {
        this.option = config;
    }

    @Override
    public void run() {
        option.setValue(!option.getValue());
        MinecraftClient.getInstance().player.sendMessage(StyleFormatter.formatText(
                new FluidText(
                        StringUtil.translate("betterblockoutline.message.toggle").formatted(StringUtil.translate(option.getNameKey()))
                )
        ), true);
    }
}
