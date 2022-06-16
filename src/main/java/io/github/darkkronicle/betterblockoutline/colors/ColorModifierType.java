package io.github.darkkronicle.betterblockoutline.colors;

import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;

public enum ColorModifierType implements OptionListEntry<ColorModifierType> {
    CHROMA("chroma", ChromaColorModifier::new),
    BLINK("blink", BlinkColorModifier::new),
    TINT("tint", BlockTintModifier::new),
    TOOL("tool", ToolColorModifier::new)
    ;

    private final String value;
    @Getter
    private final Supplier<IColorModifier> colorModifier;

    ColorModifierType(String value, Supplier<IColorModifier> colorSupplier) {
        this.value = value;
        this.colorModifier = colorSupplier;
    }

    @Override
    public List<ColorModifierType> getAll() {
        return List.of(values());
    }

    @Override
    public String getSaveKey() {
        return value;
    }

    @Override
    public String getDisplayKey() {
        return "betterblockoutline.colormodifiertype." + value;
    }

    @Override
    public String getInfoKey() {
        return "betterblockoutline.colormodifiertype.info." + value;
    }
}
