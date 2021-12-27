package io.github.darkkronicle.betterblockoutline.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.darkkronicle.betterblockoutline.config.gui.ConfigScreen;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreen();
    }

}
