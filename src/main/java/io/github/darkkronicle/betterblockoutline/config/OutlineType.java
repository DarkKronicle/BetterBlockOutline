package io.github.darkkronicle.betterblockoutline.config;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Type of way to render the outline of the block
 */
@AllArgsConstructor
public enum OutlineType implements OptionListEntry<OutlineType> {
    LINE("line"),
    STRIP("strip")
    ;

    private final String configValue;

    @Override
    public List<OutlineType> getAll() {
        return List.of(values());
    }

    @Override
    public String getSaveKey() {
        return configValue;
    }

    @Override
    public String getDisplayKey() {
        return "betterblockoutline.config.outlinetype." + configValue;
    }

    @Override
    public String getInfoKey() {
        return "betterblockoutline.config.outlinetype.info." + configValue;
    }

}
