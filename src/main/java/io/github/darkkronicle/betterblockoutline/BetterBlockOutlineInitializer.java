package io.github.darkkronicle.betterblockoutline;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.RenderEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.config.gui.ColorModifierListScreen;
import io.github.darkkronicle.betterblockoutline.config.gui.ConfigScreen;
import io.github.darkkronicle.betterblockoutline.config.gui.GuiTabManager;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.HotkeyCallbacks;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.Hotkeys;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.InputHandler;
import io.github.darkkronicle.betterblockoutline.renderers.BasicOutlineRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo2dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo3dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.PersistentOutlineRenderer;
import io.github.darkkronicle.kommandlib.CommandManager;
import io.github.darkkronicle.kommandlib.command.ClientCommand;
import io.github.darkkronicle.kommandlib.command.CommandInvoker;
import io.github.darkkronicle.kommandlib.invokers.BaseCommandInvoker;
import io.github.darkkronicle.kommandlib.util.CommandUtil;
import io.github.darkkronicle.kommandlib.util.InfoUtil;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class BetterBlockOutlineInitializer implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(BetterBlockOutline.MOD_ID, new ConfigStorage());

        GuiTabManager tabs = GuiTabManager.getInstance();
        tabs.add(GuiTabManager.wrapSaveableOptions("general", ConfigStorage.General.OPTIONS));
        tabs.add(GuiTabManager.children(
                "color_mods",
                GuiTabManager.wrapScreen("outline_mods", (parent) -> new ColorModifierListScreen(ColorModifierListScreen.Type.OUTLINE)),
                GuiTabManager.wrapScreen("fill_mods", (parent) -> new ColorModifierListScreen(ColorModifierListScreen.Type.FILL)))
        );
        tabs.add(GuiTabManager.children("blockinfo",
                GuiTabManager.wrapSaveableOptions("blockinfo.blockinfo2d", () -> {
                    List<SaveableConfig<? extends IConfigBase>> info = new ArrayList<>(ConfigStorage.BlockInfo2d.OPTIONS);
                    info.addAll(BlockInfo2dRenderer.getInstance().getActiveConfigs());
                    return info;
                }),
                GuiTabManager.children("blockinfo.blockinfo3d",
                        GuiTabManager.wrapSaveableOptions("blockinfo.blockinfo3d.general", () -> {
                            List<SaveableConfig<? extends IConfigBase>> info = new ArrayList<>(ConfigStorage.BlockInfo3d.OPTIONS);
                            info.addAll(BlockInfo3dRenderer.getInstance().getActiveConfigs());
                            return info;
                        }),
                        GuiTabManager.wrapSaveableOptions("blockinfo.blockinfo3d.directionarrow", ConfigStorage.BlockInfoDirectionArrow.OPTIONS)
                )
        ));

        tabs.add(GuiTabManager.children("hotkeys",
                GuiTabManager.wrapSaveableOptions("hotkeys.general", Hotkeys.OPTIONS),
                // Use suppliers since they can be updated
                GuiTabManager.wrapSaveableOptions("hotkeys.blockinfo2d", () -> BlockInfo2dRenderer.getInstance().getHotkeyConfigs()),
                GuiTabManager.wrapSaveableOptions("hotkeys.blockinfo3d", () -> BlockInfo3dRenderer.getInstance().getHotkeyConfigs())
        ));

        BlockInfo2dRenderer.getInstance().setup();
        BlockInfo3dRenderer.getInstance().setup();

        BlockOutlineManager.getInstance().add(new BasicOutlineRenderer());
        BlockOutlineManager.getInstance().add(BlockInfo2dRenderer.getInstance());
        BlockOutlineManager.getInstance().add(BlockInfo3dRenderer.getInstance());
        BlockOutlineManager.getInstance().add(PersistentOutlineRenderer.getInstance());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());

        HotkeyCallbacks.setup();

        CommandInvoker<ServerCommandSource> base = new BaseCommandInvoker(
                BetterBlockOutline.MOD_ID,
                "betterblockoutline",
                CommandUtil.literal("betterblockoutline").executes(ClientCommand.of(context -> InfoUtil.sendChatMessage("BetterBlockOutline by DarkKronicle"))).build()
        );
        CommandManager.getInstance().addCommand(base);
    }

}
