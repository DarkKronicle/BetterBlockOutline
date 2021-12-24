package io.github.darkkronicle.betterblockoutline.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OutlineType implements IConfigOptionListEntry {
    LINE("line"),
    STRIP("strip")
    ;

    private final String configValue;

    @Override
    public String getStringValue() {
        return configValue;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate("betterblockoutline.config.outlinetype." + configValue);
    }

    @Override
    public OutlineType cycle(boolean forward) {
        int index = ordinal();
        if (forward) {
            index++;
        } else {
            index--;
        }
        return values()[index % values().length];
    }

    @Override
    public OutlineType fromString(String value) {
        for (OutlineType type : values()) {
            if (type.getStringValue().equals(value)) {
                return type;
            }
        }
        return LINE;
    }
}
