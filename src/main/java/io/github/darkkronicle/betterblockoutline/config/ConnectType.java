package io.github.darkkronicle.betterblockoutline.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;
import lombok.AllArgsConstructor;

/**
 * Type of way to connect blocks rendered over.;
 */
@AllArgsConstructor
public enum ConnectType implements IConfigOptionListEntry {
    NONE("none"),
    BLOCKS("blocks"),
    SEAMLESS("seamless")
    ;

    private final String configValue;

    @Override
    public String getStringValue() {
        return configValue;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate("betterblockoutline.config.connecttype." + configValue);
    }

    @Override
    public ConnectType cycle(boolean forward) {
        int index = ordinal();
        if (forward) {
            index++;
        } else {
            index--;
        }
        return values()[index % values().length];
    }

    @Override
    public ConnectType fromString(String value) {
        for (ConnectType type : values()) {
            if (type.getStringValue().equals(value)) {
                return type;
            }
        }
        return NONE;
    }
}
