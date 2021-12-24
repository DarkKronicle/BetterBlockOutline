package io.github.darkkronicle.betterblockoutline.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends GuiConfigsBase {

    public ConfigScreen() {
        super(10, 30, BetterBlockOutline.MOD_ID, null, "betterblockoutline.screen.main");
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>(ConfigStorage.General.OPTIONS);
        List<IConfigBase> config = new ArrayList<>();
        for (SaveableConfig<? extends IConfigBase> s : configs) {
            config.add(s.config);
        }

        return ConfigOptionWrapper.createFor(config);
    }

}
