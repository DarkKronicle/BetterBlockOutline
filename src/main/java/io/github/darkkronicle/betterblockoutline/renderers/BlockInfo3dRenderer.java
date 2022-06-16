package io.github.darkkronicle.betterblockoutline.renderers;

import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.InfestedSilverfish;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.AbstractBlockInfo3d;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.DirectionArrow;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Renderer for {@link AbstractBlockInfo3d}
 */
public class BlockInfo3dRenderer implements IOverlayRenderer {

    private final static BlockInfo3dRenderer INSTANCE = new BlockInfo3dRenderer();

    public static BlockInfo3dRenderer getInstance() {
        return INSTANCE;
    }

    @Getter
    private final List<AbstractBlockInfo3d> renderers = new ArrayList<>();

    private BlockInfo3dRenderer() {}

    public void add(AbstractBlockInfo3d blockInfo) {
        renderers.add(blockInfo);
    }

    public void setup() {
        add(new DirectionArrow());
        add(new InfestedSilverfish());
        Collections.sort(renderers);
    }

    public List<HotkeySettings> getHotkeys() {
        List<HotkeySettings> keys = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            keys.add(info.getActiveKey().getValue());
        }
        return keys;
    }

    public List<HotkeySettingsOption> getHotkeyConfigs() {
        List<HotkeySettingsOption> keys = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            keys.add(info.getActiveKey());
        }
        return keys;
    }

    public List<BooleanOption> getActiveConfigs() {
        List<BooleanOption> active = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            active.add(info.getActive());
        }
        return active;
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (!ConfigStorage.getBlockInfo3d().getActive().getValue()) {
            return false;
        }
        for (AbstractBlockInfo3d renderer : renderers) {
            if (renderer.render(matrices, camera, entity, block)) {
                return true;
            }
        }
        return false;
    }
}
