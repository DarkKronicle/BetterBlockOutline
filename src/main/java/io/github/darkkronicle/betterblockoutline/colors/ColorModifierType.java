package io.github.darkkronicle.betterblockoutline.colors;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import lombok.Getter;

import java.util.function.Supplier;

public enum ColorModifierType implements IConfigOptionListEntry {
    CHROMA("chroma", ChromaColorModifier::new),
    BLINK("blink", BlinkColorModifier::new)
    ;

    private final String value;
    @Getter
    private final Supplier<IColorModifier> colorModifier;

    ColorModifierType(String value, Supplier<IColorModifier> colorSupplier) {
        this.value = value;
        this.colorModifier = colorSupplier;
    }

    @Override
    public String getStringValue() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate("betterblockoutline.colormodifiertype." + value);
    }

    @Override
    public ColorModifierType cycle(boolean forward) {
        int index = ordinal();
        if (forward) {
            index++;
        } else {
            index--;
        }
        return values()[index % values().length];
    }

    @Override
    public ColorModifierType fromString(String value) {
        for (ColorModifierType type : values()) {
            if (type.getStringValue().equals(value)) {
                return type;
            }
        }
        return CHROMA;
    }
}
