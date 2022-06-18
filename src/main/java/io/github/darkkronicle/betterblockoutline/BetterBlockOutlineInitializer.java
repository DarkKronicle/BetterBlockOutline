package io.github.darkkronicle.betterblockoutline;

import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.config.gui.colormods.ColorModifierComponent;
import io.github.darkkronicle.betterblockoutline.config.gui.colormods.ColorModifierConfig;
import io.github.darkkronicle.betterblockoutline.config.gui.colormods.ColorModifierSelectorComponent;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.HotkeyCallbacks;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.renderers.BasicOutlineRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo2dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo3dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.PersistentOutlineRenderer;
import io.github.darkkronicle.darkkore.gui.OptionComponentHolder;
import io.github.darkkronicle.darkkore.intialization.Initializer;

import java.util.Optional;


public class BetterBlockOutlineInitializer implements Initializer {

    @Override
    public void init() {

        BlockOutlineManager.getInstance().add(new BasicOutlineRenderer());
        BlockOutlineManager.getInstance().add(BlockInfo2dRenderer.getInstance());
        BlockOutlineManager.getInstance().add(BlockInfo3dRenderer.getInstance());
        BlockOutlineManager.getInstance().add(PersistentOutlineRenderer.getInstance());

        HotkeyCallbacks.setup();

        OptionComponentHolder.getInstance().addWithOrder(0, (parent, option, width) -> {
            if (!(option instanceof ConfigColorModifier<?>)) {
                return Optional.empty();
            }
            return Optional.of(new ColorModifierComponent<>(parent, (ConfigColorModifier<? extends IColorModifier>) option, width));
        });
        OptionComponentHolder.getInstance().addWithOrder(0, ((parent, option, width) -> {
            if (option instanceof ColorModifierConfig) {
                return Optional.of(new ColorModifierSelectorComponent(parent, (ColorModifierConfig) option, width));
            }
            return Optional.empty();
        }));

    }

}
