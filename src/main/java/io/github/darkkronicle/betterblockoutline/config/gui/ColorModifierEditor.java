package io.github.darkkronicle.betterblockoutline.config.gui;

import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.Tab;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import java.util.List;


public class ColorModifierEditor<T extends IColorModifier> extends ConfigScreen {

    public ColorModifierEditor(ConfigColorModifier<T> modifier, Screen parent) {
        super(List.of(Tab.ofOptions(new Identifier(BetterBlockOutline.MOD_ID, "main"), "main", modifier.getOptions())));
        this.setParent(parent);
    }

}
