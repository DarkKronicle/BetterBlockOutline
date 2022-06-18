package io.github.darkkronicle.betterblockoutline.config.hotkeys;

import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo2dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo3dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.PersistentOutlineRenderer;
import io.github.darkkronicle.darkkore.hotkeys.BasicHotkey;
import io.github.darkkronicle.darkkore.hotkeys.Hotkey;
import io.github.darkkronicle.darkkore.hotkeys.HotkeyHandler;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class HotkeyCallbacks {

    public static void setup() {
        HotkeyHandler.getInstance().add(BetterBlockOutline.MOD_ID, "generalhotkeys", () -> {
            List<Hotkey> hotkeys = new ArrayList<>();
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getToggleModActive().getValue(), new KeyToggleBoolean(ConfigStorage.getGeneral().getActive())));
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getToggleInfo3dActive().getValue(), new KeyToggleBoolean(ConfigStorage.getBlockInfo3d().getActive())));
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getToggleInfo2dActive().getValue(), new KeyToggleBoolean(ConfigStorage.getBlockInfo2d().getActive())));
            hotkeys.add(new BasicHotkey(ConfigStorage.getBlockInfoArrow().getCycleArrow().getValue(), new KeyCycleOption<>(ConfigStorage.getBlockInfoArrow().getArrowType())));
            for (AbstractBlockInfo info : BlockInfo2dRenderer.getInstance().getRenderers()) {
                hotkeys.add(new BasicHotkey(info.getActiveKey().getValue(), new KeyToggleBoolean(info.getActive())));
            }
            for (AbstractBlockInfo info : BlockInfo3dRenderer.getInstance().getRenderers()) {
                hotkeys.add(new BasicHotkey(info.getActiveKey().getValue(), new KeyToggleBoolean(info.getActive())));
            }

            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getMenu().getValue(), () -> MinecraftClient.getInstance().setScreen(ConfigStorage.getInstance().getScreen())));
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getDisableAllInfo2d().getValue(), () -> {
                for (AbstractBlockInfo info : BlockInfo2dRenderer.getInstance().getRenderers()) {
                    info.getActive().setValue(false);
                }
                MinecraftClient.getInstance().player.sendMessage(StringUtil.translateToText("betterblockoutline.message.disableall2d"), true);
            }));
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getDisableAllInfo3d().getValue(), () -> {
                for (AbstractBlockInfo info : BlockInfo3dRenderer.getInstance().getRenderers()) {
                    info.getActive().setValue(false);
                }
                MinecraftClient.getInstance().player.sendMessage(StringUtil.translateToText("betterblockoutline.message.disableall3d"), true);
            }));
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getTogglePersistentForBlock().getValue(), () -> {
                if (PersistentOutlineRenderer.getInstance().toggleFromPlayer()) {
                    MinecraftClient.getInstance().player.sendMessage(StringUtil.translateToText("betterblockoutline.message.toggleblock.success"));
                } else {
                    MinecraftClient.getInstance().player.sendMessage(StringUtil.translateToText("betterblockoutline.message.toggleblock.failure"));
                }
            }));
            hotkeys.add(new BasicHotkey(Hotkeys.getInstance().getClearPersistentBlocks().getValue(), () -> {
                PersistentOutlineRenderer.getInstance().clear();
                MinecraftClient.getInstance().player.sendMessage(StringUtil.translateToText("betterblockoutline.message.clearedblocks"), true);
            }));
            return hotkeys;
        });
    }

}
