package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import fi.dy.masa.malilib.hotkeys.IMouseInputHandler;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo2dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo3dRenderer;

import java.util.Collections;

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
        for (AbstractBlockInfo info : BlockInfo2dRenderer.getInstance().getRenderers()) {
            manager.addKeybindToMap(info.getActiveKey().config.getKeybind());
        }
        for (AbstractBlockInfo info : BlockInfo3dRenderer.getInstance().getRenderers()) {
            manager.addKeybindToMap(info.getActiveKey().config.getKeybind());
        }
        manager.addKeybindToMap(ConfigStorage.BlockInfoDirectionArrow.CYCLE_ARROW.config.getKeybind());
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(BetterBlockOutline.MOD_ID, "betterblockoutline.hotkeys.category.general", Hotkeys.RAW_KEYBINDS);
        manager.addHotkeysForCategory(BetterBlockOutline.MOD_ID, "betterblockoutline.hotkeys.category.blockinfo2d", BlockInfo2dRenderer.getInstance().getHotkeys());
        manager.addHotkeysForCategory(BetterBlockOutline.MOD_ID, "betterblockoutline.hotkeys.category.blockinfo3d", BlockInfo3dRenderer.getInstance().getHotkeys());
        manager.addHotkeysForCategory(BetterBlockOutline.MOD_ID, "betterblockoutline.hotkeys.category.blockinfo3d", Collections.singletonList(ConfigStorage.BlockInfoDirectionArrow.CYCLE_ARROW.config));
    }
}
