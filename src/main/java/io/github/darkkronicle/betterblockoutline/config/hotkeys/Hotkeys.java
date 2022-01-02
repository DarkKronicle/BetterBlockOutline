package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Hotkeys {

    public static final String NAME = "hotkeys";

    public final static SaveableConfig<ConfigHotkey> MENU = SaveableConfig.fromConfig("menu",
            new ConfigHotkey("betterblockoutline.config.hotkey.menu", "", "betterblockoutline.config.hotkey.info.menu"));

    public final static SaveableConfig<ConfigHotkey> TOGGLE_MOD_ACTIVE = SaveableConfig.fromConfig("toggleModActive",
            new ConfigHotkey("betterblockoutline.config.hotkey.togglemodactive", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.togglemodactive"));

    public final static SaveableConfig<ConfigHotkey> TOGGLE_INFO2D_ACTIVE = SaveableConfig.fromConfig("toggleInfoActive",
            new ConfigHotkey("betterblockoutline.config.hotkey.toggleinfoactive2d", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.toggleinfoactive2d"));

    public final static SaveableConfig<ConfigHotkey> DISABLE_ALL_INFO2D = SaveableConfig.fromConfig("disableAllInfo",
            new ConfigHotkey("betterblockoutline.config.hotkey.disableallinfo2d", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.disableallinfo2d"));

    public final static SaveableConfig<ConfigHotkey> TOGGLE_INFO3D_ACTIVE = SaveableConfig.fromConfig("toggleInfoActive3d",
            new ConfigHotkey("betterblockoutline.config.hotkey.toggleinfoactive3d", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.toggleinfoactive3d"));

    public final static SaveableConfig<ConfigHotkey> DISABLE_ALL_INFO3D = SaveableConfig.fromConfig("disableAllInfo3d",
            new ConfigHotkey("betterblockoutline.config.hotkey.disableallinfo3d", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.disableallinfo3d"));


    public final static ImmutableList<SaveableConfig<ConfigHotkey>> KEYS = ImmutableList.of(MENU, TOGGLE_MOD_ACTIVE, TOGGLE_INFO2D_ACTIVE, DISABLE_ALL_INFO2D, TOGGLE_INFO3D_ACTIVE, DISABLE_ALL_INFO3D);

    public final static ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(MENU, TOGGLE_MOD_ACTIVE, TOGGLE_INFO2D_ACTIVE, DISABLE_ALL_INFO2D, TOGGLE_INFO3D_ACTIVE, DISABLE_ALL_INFO3D);

    public final static ImmutableList<ConfigHotkey> RAW_KEYBINDS = Util.make(() -> {
        List<ConfigHotkey> keys = new ArrayList<>();
        for (SaveableConfig<ConfigHotkey> hotkey : KEYS) {
            keys.add(hotkey.config);
        }
        return ImmutableList.copyOf(keys);
    });


}
