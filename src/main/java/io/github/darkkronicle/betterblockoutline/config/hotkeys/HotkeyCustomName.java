package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import org.jetbrains.annotations.Nullable;

public class HotkeyCustomName extends ConfigHotkey {

    private final String prettyReplacement;
    private final String prettyTranslation;
    private final String comment;

    public HotkeyCustomName(String name, String defaultStorageString, KeybindSettings settings, String comment, String prettyReplacement) {
        super(name, defaultStorageString, settings, comment);
        this.prettyReplacement = prettyReplacement;
        this.prettyTranslation = name;
        this.comment = comment;
    }

    @Override
    public String getComment() {
        return StringUtils.translate(comment).replace("<hotkey>", StringUtils.translate(prettyReplacement));
    }

    @Override
    public String getName() {
        String pretty = StringUtils.translate(this.prettyTranslation);
        String replacement = StringUtils.translate(prettyReplacement);
        return pretty.replace("<hotkey>", replacement);
    }
}
