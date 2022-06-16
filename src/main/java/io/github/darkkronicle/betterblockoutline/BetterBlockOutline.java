package io.github.darkkronicle.betterblockoutline;

import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.darkkore.config.ConfigurationManager;
import io.github.darkkronicle.darkkore.intialization.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BetterBlockOutline implements ClientModInitializer {

    public final static String MOD_ID = "betterblockoutline";

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializer(MOD_ID, 1, new BetterBlockOutlineInitializer());
        ConfigurationManager.getInstance().add(ConfigStorage.getInstance());
    }

}
