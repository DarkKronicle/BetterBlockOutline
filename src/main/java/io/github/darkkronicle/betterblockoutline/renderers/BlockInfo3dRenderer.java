package io.github.darkkronicle.betterblockoutline.renderers;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.AbstractBlockInfo3d;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.DirectionArrow;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
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
        Collections.sort(renderers);
    }

    /**
     * Returns all of the hotkeys used for the renderers
     */
    public List<ConfigHotkey> getHotkeys() {
        List<ConfigHotkey> keys = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            keys.add(info.getActiveKey().config);
        }
        return keys;
    }

    /**
     * Returns all of the {@link SaveableConfig} for hotkeys
     */
    public List<SaveableConfig<? extends IConfigBase>> getHotkeyConfigs() {
        List<SaveableConfig<? extends IConfigBase>> keys = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            keys.add(info.getActiveKey());
        }
        return keys;
    }

    /**
     * Returns all of the {@link SaveableConfig} for whether an info renderer is active
     * @return
     */
    public List<SaveableConfig<? extends IConfigBase>> getActiveConfigs() {
        List<SaveableConfig<? extends IConfigBase>> active = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            active.add(info.getActive());
        }
        return active;
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        for (AbstractBlockInfo3d renderer : renderers) {
            if (renderer.render(matrices, camera, entity, block)) {
                return true;
            }
        }
        return false;
    }
}
