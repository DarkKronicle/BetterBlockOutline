package io.github.darkkronicle.betterblockoutline.info;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;

public abstract class AbstractBlockInfo implements IOverlayRenderer {

    @Getter
    private final SaveableConfig<ConfigBoolean> active;

    public abstract boolean shouldRender(AbstractConnectedBlock block);

    public abstract void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block);

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (shouldRender(block)) {
            renderInfo(matrices, camera, entity, block);
            return true;
        }
        return false;
    }

    public AbstractBlockInfo(String name, String translationName, String translationHover) {
        active = SaveableConfig.fromConfig(name, new ConfigBoolean(translationName, false, translationHover));
    }


}
