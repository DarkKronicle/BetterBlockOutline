package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import com.google.common.collect.ImmutableList;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class Hotkeys {

    public static final String NAME = "hotkeys";

    private final static Hotkeys INSTANCE = new Hotkeys();

    public static Hotkeys getInstance() {
        return INSTANCE;
    }

    @Getter
    private final HotkeySettingsOption menu = new HotkeySettingsOption("menu",
            "betterblockoutline.config.hotkey.menu", "betterblockoutline.config.hotkey.info.menu",
            new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption toggleModActive = new HotkeySettingsOption("toggleModActive",
            "betterblockoutline.config.hotkey.togglemodactive", "betterblockoutline.config.hotkey.info.togglemodactive",
            new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption toggleInfo2dActive = new HotkeySettingsOption("toggleInfoActive",
            "betterblockoutline.config.hotkey.toggleinfoactive2d", "betterblockoutline.config.hotkey.info.toggleinfoactive2d",
            new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption disableAllInfo2d = new HotkeySettingsOption("disableAllInfo",
            "betterblockoutline.config.hotkey.disableallinfo2d", "betterblockoutline.config.hotkey.info.disableallinfo2d",
            new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption toggleInfo3dActive = new HotkeySettingsOption("toggleInfoActive3d",
            "betterblockoutline.config.hotkey.toggleinfoactive3d", "betterblockoutline.config.hotkey.info.toggleinfoactive3d",
            new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption disableAllInfo3d = new HotkeySettingsOption("disableAllInfo3d",
            "betterblockoutline.config.hotkey.disableallinfo3d", "betterblockoutline.config.hotkey.info.disableallinfo3d",
            new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption togglePersistentForBlock = new HotkeySettingsOption("togglePersistentForBlock",
            "betterblockoutline.config.hotkey.togglepersistentforblock", "betterblockoutline.config.hotkey.info.togglepersistentforblock",
            new HotkeySettings(false, false, false, List.of(GLFW.GLFW_KEY_N), PlayerContextCheck.getDefault()));

    @Getter
    private final HotkeySettingsOption clearPersistentBlocks = new HotkeySettingsOption("clearPersistentBlocks",
            "betterblockoutline.config.hotkey.clearpersistentblocks", "betterblockoutline.config.hotkey.info.clearpersistentblocks",
            new HotkeySettings(false, false, false, List.of(GLFW.GLFW_KEY_N), PlayerContextCheck.getDefault()));


    @Getter
    private final ImmutableList<Option<?>> options = ImmutableList.of(menu, toggleModActive, toggleInfo2dActive, disableAllInfo2d, toggleInfo3dActive, disableAllInfo3d, togglePersistentForBlock, clearPersistentBlocks);


}
