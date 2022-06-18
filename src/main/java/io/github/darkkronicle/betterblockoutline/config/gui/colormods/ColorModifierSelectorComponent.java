package io.github.darkkronicle.betterblockoutline.config.gui.colormods;

import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.impl.CycleComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ColorModifierSelectorComponent extends OptionComponent<ColorModifierType, ColorModifierConfig> {

    public ColorModifierSelectorComponent(Screen parent, ColorModifierConfig option, int width) {
        super(parent, option, width, 18);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("betterblockoutline.optiontype.createnewmod"));
    }

    @Override
    public void setValue(ColorModifierType newValue) {
        this.option.setValue(newValue);
    }

    @Override
    public Component getMainComponent() {
        ListComponent component = new ListComponent(parent, -1, -1, false);
        component.setTopPad(0);
        component.setLeftPad(0);
        component.setBottomPad(0);
        component.setRightPad(0);
        CycleComponent<ColorModifierType> cycle = new CycleComponent<>(
                parent, option.getValue(), -1, 14, new Color(100, 100, 100, 100), new Color(150, 150, 150, 150), this::setValue
        );
        cycle.setHeight(14);
        component.addComponent(cycle);
        ButtonComponent addNew = new ButtonComponent(parent, -1, 14, StringUtil.translateToText("betterblockoutline.button.createnew"), new Color(100, 100, 100, 100), new Color(150, 150, 150, 150), button -> {
            ConfigStorage.getInstance().addColorMod(option.getContext().getConfigValue(), new ConfigColorModifier<>(option.getValue()));
            // Force building of stuff
            MinecraftClient.getInstance().setScreen(ConfigStorage.getInstance().getScreen());
        });
        component.addComponent(addNew);
        return component;
    }

}
