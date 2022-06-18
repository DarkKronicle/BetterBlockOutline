package io.github.darkkronicle.betterblockoutline.config.gui.colormods;

import io.github.darkkronicle.betterblockoutline.colors.ColorModifierContext;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import lombok.Getter;

public class ColorModifierConfig extends BasicOption<ColorModifierType> {

    @Getter
    private final ColorModifierContext context;

    public ColorModifierConfig(ColorModifierContext context) {
        super("colorMod", "betterblockoutline.config.option.createnewmod", "betterblockoutline.config.option.info.createnewmod", ColorModifierType.CHROMA);
        this.context = context;
    }

}
