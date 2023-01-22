package io.github.darkkronicle.betterblockoutline.blockinfo.info3d;

import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Vector3d;

public abstract class AbstractBlockInfo3d extends AbstractBlockInfo implements IOverlayRenderer {

    public AbstractBlockInfo3d(Order order, String name, String translationName, String translationHover) {
        super(order, name, translationName, translationHover);
    }

    /**
     * Renders the current outline block.
     * @return True if it should not render other outlines, false if it should.
     */
    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (isActive() && shouldRender(block)) {
            renderInfo(matrices, camera, entity, block);
            return true;
        }
        return false;
    }

    /**
     * Renders the block information
     * @param matrices {@link MatrixStack} matrices
     * @param camera A vector containing the {@link net.minecraft.client.render.Camera} position
     * @param entity The {@link Entity} that is requesting the block info. (Should just be a {@link net.minecraft.entity.player.PlayerEntity}
     * @param block {@link AbstractConnectedBlock} containing information of currently hovered block.
     */
    public abstract void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block);

}
