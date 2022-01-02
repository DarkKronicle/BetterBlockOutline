package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.StringUtils;

public class KeyCycleOption implements IHotkeyCallback {

    private final ConfigOptionList option;

    public KeyCycleOption(ConfigOptionList option) {
        this.option = option;
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        option.setOptionListValue(option.getOptionListValue().cycle(true));
        String message = StringUtils.translate("betterblockoutline.message.cycleoption", option.getPrettyName(), option.getOptionListValue().getDisplayName());
        InfoUtils.printActionbarMessage(message);
        return true;
    }
}
