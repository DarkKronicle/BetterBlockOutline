package io.github.darkkronicle.betterblockoutline.config.gui;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

public class ColorModifierEditor extends GuiConfigsBase {

    private final ConfigColorModifier modifier;

    public ColorModifierEditor(ConfigColorModifier modifier, Screen parent) {
        super(10, 26, BetterBlockOutline.MOD_ID, parent, "title");
        setTitle(modifier.getName());
        this.modifier = modifier;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(modifier.getOptions());
    }
}
