package io.github.darkkronicle.betterblockoutline.config.gui;

import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;


public class ColorModifierEditor<T extends IColorModifier> extends ConfigScreen {

    public ColorModifierEditor(ConfigColorModifier<T> modifier, Screen parent) {
        super(modifier.getOptions());
        this.setParent(parent);
    }

}
