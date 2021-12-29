package io.github.darkkronicle.betterblockoutline.config.gui;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.gui.colormods.WidgetColorModifier;
import io.github.darkkronicle.betterblockoutline.config.gui.colormods.WidgetListColorModifiers;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Based off of https://github.com/maruohon/minihud/blob/fabric_1.17_snapshots_temp_features/src/main/java/fi/dy/masa/minihud/gui/GuiShapeManager.java
public class ColorModifierListScreen extends GuiListBase<ConfigColorModifier, WidgetColorModifier, WidgetListColorModifiers> {

    @AllArgsConstructor
    public enum Type {
        OUTLINE("outline"),
        FILL("fill")
        ;

        @Getter
        private final String configKey;
    }

    protected final WidgetDropDownList<ColorModifierType> typeDropDown;
    private final Type type;

    public ColorModifierListScreen(Type type) {
        super(10, 60);
        this.title = StringUtils.translate("betterblockoutline.screen.main");
        this.type = type;

        this.typeDropDown = new WidgetDropDownList<>(0, 0, 160, 18, 200, 10, ImmutableList.copyOf(ColorModifierType.values()), ColorModifierType::getDisplayName);
        this.typeDropDown.setZLevel(this.getZOffset() + 100);
    }

    @Override
    public void initGui() {
        if (type == Type.OUTLINE) {
            ConfigScreen.TAB = GuiTab.OUTLINE_MODS;
        } else if (type == Type.FILL) {
            ConfigScreen.TAB = GuiTab.FILL_MODS;
        }

        int y = 26;

        super.initGui();
        y = (22 * ConfigScreen.addButtons(this, 10, 26)) + y;

        String addModifierName = StringUtils.translate("betterblockoutline.config.button.addmodifier");
        ButtonGeneric addModifier = new ButtonGeneric(this.width - 10, y, -1, true, addModifierName);
        this.typeDropDown.setPosition(addModifier.getX() - typeDropDown.getWidth() - 4, y + 1);
        this.addWidget(typeDropDown);

        this.setListPosition(this.getListX(), y + 20);
        this.reCreateListWidget();

        this.addButton(addModifier, (button, mouseButton) -> {
            if (typeDropDown.getSelectedEntry() == null) {
                InfoUtils.showGuiOrInGameMessage(Message.MessageType.ERROR, "betterblockoutline.error.noselectedmodifier");
                return;
            }
            ConfigStorage.addColorMod(type.getConfigKey(), new ConfigColorModifier(typeDropDown.getSelectedEntry()));
            getListWidget().refreshEntries();
        });

        getListWidget().refreshEntries();
    }

    @Override
    protected WidgetListColorModifiers createListWidget(int listX, int listY) {
        return new WidgetListColorModifiers(listX, listY, getBrowserWidth(), getBrowserHeight(), null, type, this);
    }

    @Override
    protected int getBrowserWidth() {
        return this.width - 20;
    }

    @Override
    protected int getBrowserHeight() {
        return this.height - 6 - this.getListY();
    }
}
