package io.github.darkkronicle.betterblockoutline;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BetterBlockOutline implements ClientModInitializer {

    public final static String MOD_ID = "betterblockoutline";

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new BetterBlockOutlineInitializer());
    }

}
