package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import fi.dy.masa.malilib.hotkeys.IMouseInputHandler;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.info.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.renderers.InfoRenderer;

// https://github.com/vacla/Watson/blob/fabric_1.17/src/main/java/eu/minemania/watson/event/InputHandler.java#L16:13
public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {

    private final static InputHandler INSTANCE = new InputHandler();

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    private InputHandler() {}

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (ConfigHotkey keybind : Hotkeys.RAW_KEYBINDS) {
            manager.addKeybindToMap(keybind.getKeybind());
        }
        for (AbstractBlockInfo info : InfoRenderer.getInstance().getRenderers()) {
            manager.addKeybindToMap(info.getActiveKey().config.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(BetterBlockOutline.MOD_ID, "betterblockoutline.hotkeys.category.general", Hotkeys.RAW_KEYBINDS);
        manager.addHotkeysForCategory(BetterBlockOutline.MOD_ID, "betterblockoutline.hotkeys.category.blockinfo", InfoRenderer.getInstance().getHotkeys());
    }
}
