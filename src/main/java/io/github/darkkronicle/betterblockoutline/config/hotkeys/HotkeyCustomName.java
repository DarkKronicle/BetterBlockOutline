package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;

public class HotkeyCustomName extends ConfigHotkey {

    private final String prettyReplacement;
    private final String prettyTranslation;

    public HotkeyCustomName(String name, String defaultStorageString, KeybindSettings settings, String comment, String prettyReplacement) {
        super(name, defaultStorageString, settings, comment);
        this.prettyReplacement = prettyReplacement;
        this.prettyTranslation = name;
    }

    @Override
    public String getPrettyName() {
        String pretty = StringUtils.translate(this.prettyTranslation);
        String replacement = StringUtils.translate(prettyReplacement);
        return pretty.replace("<hotekey>", replacement);
    }
}
