package io.github.darkkronicle.betterblockoutline.colors;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public enum ColorModifierContext implements OptionListEntry<ColorModifierContext> {
    FILL("fill"),
    OUTLINE("outline")
    ;

    @Getter
    private final String configValue;

    @Override
    public List<ColorModifierContext> getAll() {
        return List.of(values());
    }

    @Override
    public String getSaveKey() {
        return configValue;
    }

    @Override
    public String getDisplayKey() {
        return "betterblockoutline.config.tab." + configValue;
    }

    @Override
    public String getInfoKey() {
        return "betterblockoutline.config.tab.info." + configValue;
    }
}
