package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.gui.ConfigScreen;
import io.github.darkkronicle.betterblockoutline.info.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.renderers.InfoRenderer;

public class HotkeyCallbacks {

    public static void setup() {
        Callbacks callback = new Callbacks();
        Hotkeys.MENU.config.getKeybind().setCallback(callback);
        Hotkeys.DISABLE_ALL_INFO.config.getKeybind().setCallback(callback);
        Hotkeys.TOGGLE_INFO_ACTIVE.config.getKeybind().setCallback(new KeyToggleBoolean(ConfigStorage.BlockInfo.ACTIVE.config));
        for (AbstractBlockInfo info : InfoRenderer.getInstance().getRenderers()) {
            info.getActiveKey().config.getKeybind().setCallback(new KeyToggleBoolean(info.getActive().config));
        }
    }

    public static class Callbacks implements IHotkeyCallback {

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == Hotkeys.MENU.config.getKeybind()) {
                GuiBase.openGui(new ConfigScreen());
                return true;
            }
            if (key == Hotkeys.DISABLE_ALL_INFO.config.getKeybind()) {
                for (AbstractBlockInfo info : InfoRenderer.getInstance().getRenderers()) {
                    info.getActive().config.setBooleanValue(false);
                }
                InfoUtils.printActionbarMessage("betterblockoutline.message.disableall");
            }
            return false;
        }
    }

}
