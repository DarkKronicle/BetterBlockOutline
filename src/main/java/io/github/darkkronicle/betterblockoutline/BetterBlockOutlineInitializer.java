package io.github.darkkronicle.betterblockoutline;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;

public class BetterBlockOutlineInitializer implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(BetterBlockOutline.MOD_ID, new ConfigStorage());
    }

}
