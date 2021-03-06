package io.github.darkkronicle.betterblockoutline.config.gui.colormods;

import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.gui.ColorModifierEditor;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ColorModifierComponent<T extends IColorModifier> extends OptionComponent<T, ConfigColorModifier<T>> {

    private final ConfigColorModifier<T> option;

    public ColorModifierComponent(Screen parent, ConfigColorModifier<T> option, int width) {
        super(parent, option, width, 18);
        this.option = option;
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("betterblockoutline.optiontype.info.color_modifier"));
    }

    @Override
    public void setValue(T newValue) {
        option.setValue(newValue);
    }

    @Override
    public Component getMainComponent() {
        ListComponent component = new ListComponent(parent, -1, -1, false);
        component.setLeftPad(0);
        component.setTopPad(0);
        ButtonComponent edit = new ButtonComponent(
                parent, -1, 14, StringUtil.translateToText("betterblockoutline.config.button.configure"),
                new Color(100, 100, 100, 100), new Color(150, 150, 150, 150),
                button -> MinecraftClient.getInstance().setScreen(new ColorModifierEditor<>(option, parent))
        );
        component.addComponent(edit);
        ButtonComponent delete = new ButtonComponent(
                parent, -1, 14, StringUtil.translateToText("betterblockoutline.config.button.delete"),
                new Color(100, 100, 100, 100), new Color(150, 150, 150, 150),
                button -> {
                    ConfigStorage.getInstance().deleteColorMod(option);
                    MinecraftClient.getInstance().setScreen(ConfigStorage.getInstance().getScreen());
                }
        );
        component.addComponent(delete);
        return new PositionedComponent(parent, component, 0, 0);
    }
}
