package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.util.StringUtil;

public class HotkeyCustomName extends HotkeySettingsOption {

    private final String prettyReplacement;
    private final String prettyTranslation;
    private final String comment;

    public HotkeyCustomName(String name, String displayName, String infoName, HotkeySettings settings) {
        super(name, displayName, infoName, settings);
        this.prettyReplacement = displayName;
        this.prettyTranslation = name;
        this.comment = infoName;
    }

    @Override
    public String getInfoKey() {
        return StringUtil.translate(comment).replace("<hotkey>", StringUtil.translate(prettyReplacement));
    }

    @Override
    public String getNameKey() {
        String pretty = StringUtil.translate(this.prettyTranslation);
        String replacement = StringUtil.translate(prettyReplacement);
        return pretty.replace("<hotkey>", replacement);
    }

}
