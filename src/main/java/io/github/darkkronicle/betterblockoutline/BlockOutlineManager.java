package io.github.darkkronicle.betterblockoutline;

import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BasicOutlineRenderer;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class BlockOutlineManager {

    private static final BlockOutlineManager INSTANCE = new BlockOutlineManager();


    public static BlockOutlineManager getInstance() {
        return INSTANCE;
    }

    @Setter
    @Getter
    private IOverlayRenderer renderer;

    private BlockOutlineManager() {
        // TODO add some more renderers. Could be fun.
        renderer = new BasicOutlineRenderer();
    }

    public void drawOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double camX, double camY, double camZ, BlockPos pos, BlockState state) {
        renderer.render(matrices, new Vector3d(camX, camY, camZ), entity, pos, state);

    }

}
