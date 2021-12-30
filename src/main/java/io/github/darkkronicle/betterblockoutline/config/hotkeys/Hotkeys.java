package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.netty.util.internal.StringUtil;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Hotkeys {

    public static final String NAME = "hotkeys";

    public final static SaveableConfig<ConfigHotkey> MENU = SaveableConfig.fromConfig("menu",
            new ConfigHotkey("betterblockoutline.config.hotkey.menu", "", "betterblockoutline.config.hotkey.info.menu"));

    public final static SaveableConfig<ConfigHotkey> TOGGLE_INFO_ACTIVE = SaveableConfig.fromConfig("toggleInfoActive",
            new ConfigHotkey("betterblockoutline.config.hotkey.toggleinfoactive", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.toggleinfoactive"));

    public final static SaveableConfig<ConfigHotkey> DISABLE_ALL_INFO = SaveableConfig.fromConfig("disableAllInfo",
            new ConfigHotkey("betterblockoutline.config.hotkey.disableallinfo", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.config.hotkey.info.disableallinfo"));


    public final static ImmutableList<SaveableConfig<ConfigHotkey>> KEYS = ImmutableList.of(MENU, TOGGLE_INFO_ACTIVE, DISABLE_ALL_INFO);

    public final static ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(MENU, TOGGLE_INFO_ACTIVE, DISABLE_ALL_INFO);

    public final static ImmutableList<ConfigHotkey> RAW_KEYBINDS = Util.make(() -> {
        List<ConfigHotkey> keys = new ArrayList<>();
        for (SaveableConfig<ConfigHotkey> hotkey : KEYS) {
            keys.add(hotkey.config);
        }
        return ImmutableList.copyOf(keys);
    });


}
