package io.github.darkkronicle.betterblockoutline.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

public class Hotkeys {

    public final static SaveableConfig<ConfigHotkey> MENU = SaveableConfig.fromConfig("menu",
            new ConfigHotkey("betterblockoutline.config.hotkey.menu", "", "betterblockoutline.config.hotkey.info.menu"));


    public final static ImmutableList<SaveableConfig<ConfigHotkey>> OPTIONS = ImmutableList.of(MENU);
}
