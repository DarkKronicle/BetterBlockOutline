package io.github.darkkronicle.betterblockoutline.config.hotkeys;


import io.github.darkkronicle.darkkore.config.options.ListOption;
import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import io.github.darkkronicle.darkkore.util.text.StyleFormatter;
import net.minecraft.client.MinecraftClient;

public class KeyCycleOption<T extends ListOption<V>, V extends OptionListEntry<V>> implements Runnable {

    private final T option;

    public KeyCycleOption(T option) {
        this.option = option;
    }

    public void run() {
        option.setValue(option.getValue().next(true));
        String message = StringUtil.translate("betterblockoutline.message.cycleoption");
        message = message.formatted(StringUtil.translate(option.getKey()), StringUtil.translate(option.getValue().getDisplayKey()));
        MinecraftClient.getInstance().player.sendMessage(StyleFormatter.formatText(new FluidText(message)), true);
    }
}
