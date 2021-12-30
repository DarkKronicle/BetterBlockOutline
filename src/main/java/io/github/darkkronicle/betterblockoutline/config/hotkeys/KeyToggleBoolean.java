package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;

public class KeyToggleBoolean extends KeyCallbackToggleBooleanConfigWithMessage {

    public KeyToggleBoolean(IConfigBoolean config) {
        super(config);
    }

}
