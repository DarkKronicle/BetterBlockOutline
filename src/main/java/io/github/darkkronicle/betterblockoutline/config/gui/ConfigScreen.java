package io.github.darkkronicle.betterblockoutline.config.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConfigScreen extends GuiConfigsBase {

    public static GuiTab TAB = GuiTab.GENERAL;

    public ConfigScreen() {
        super(10, 60, BetterBlockOutline.MOD_ID, null, "betterblockoutline.screen.main");
    }

    @Override
    public void initGui() {

        Screen screen = TAB.getTabSupplier().getScreen(this);
        if (screen != null) {
            GuiBase.openGui(screen);
            return;
        }

        super.initGui();
        addButtons(this, 10, 26);

    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(TAB.getTabSupplier().getOptions());
    }


    /**
     * Adds the category buttons to the selected screen
     * @param screen Screen to apply to
     * @return Amount of rows it created
     */
    public static int addButtons(GuiBase screen, int x, int y) {
        int rows = 1;
        for (GuiTab tab : GuiTab.values()) {
            int width = screen.getStringWidth(tab.getDisplayName()) + 10;

            if (x >= screen.width - width - 10)
            {
                x = 10;
                y += 22;
                ++rows;
            }

            x += createTabButton(screen, x, y, width, tab);
        }
        return rows;
    }

    public static class ButtonListenerTab implements IButtonActionListener {
        private final GuiTab tab;

        public ButtonListenerTab(GuiTab tab) {
            this.tab = tab;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            ConfigScreen.TAB = this.tab;
            GuiBase.openGui(new ConfigScreen());
        }
    }

    private static int createTabButton(GuiBase screen, int x, int y, int width, GuiTab tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(ConfigScreen.TAB != tab);
        screen.addButton(button, new ButtonListenerTab(tab));

        return button.getWidth() + 2;
    }

}
