package io.github.darkkronicle.betterblockoutline.colors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ColorModifierContext {
    FILL("fill"),
    OUTLINE("outline")
    ;

    @Getter
    private final String configValue;
}
