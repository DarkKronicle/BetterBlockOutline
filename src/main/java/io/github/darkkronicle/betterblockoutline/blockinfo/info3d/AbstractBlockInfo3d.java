package io.github.darkkronicle.betterblockoutline.blockinfo.info3d;

import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;

public abstract class AbstractBlockInfo3d extends AbstractBlockInfo implements IOverlayRenderer {

    public AbstractBlockInfo3d(Order order, String name, String translationName, String translationHover) {
        super(order, name, translationName, translationHover);
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (isActive() && shouldRender(block)) {
            renderInfo(matrices, camera, entity, block);
            return true;
        }
        return false;
    }

    public abstract void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block);

}
