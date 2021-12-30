package io.github.darkkronicle.betterblockoutline;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.HotkeyCallbacks;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.InputHandler;
import io.github.darkkronicle.betterblockoutline.renderers.InfoRenderer;

public class BetterBlockOutlineInitializer implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(BetterBlockOutline.MOD_ID, new ConfigStorage());
        InfoRenderer.getInstance().setup();
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());

        HotkeyCallbacks.setup();
    }

}
