package io.github.darkkronicle.betterblockoutline.config;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Type of way to connect blocks rendered over.;
 */
@AllArgsConstructor
public enum ConnectType implements OptionListEntry<ConnectType> {
    NONE("none"),
    BLOCKS("blocks"),
    SEAMLESS("seamless")
    ;

    private final String configValue;

    @Override
    public List<ConnectType> getAll() {
        return List.of(values());
    }

    @Override
    public String getSaveKey() {
        return configValue;
    }

    @Override
    public String getDisplayKey() {
        return "betterblockoutline.config.connecttype." + configValue;
    }

    @Override
    public String getInfoKey() {
        return "betterblockoutline.config.connecttype.info." + configValue;
    }
}
