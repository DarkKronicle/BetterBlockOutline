package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class NormalBlock extends AbstractConnectedBlock {

    private VoxelShape shape;

    public NormalBlock(BlockPosState block) {
        super(block);
    }

    @Override
    public VoxelShape getShape() {
        return shape;
    }

    @Override
    public void updateShape(MinecraftClient client, Entity entity) {
        if (ConfigStorage.General.CUBE_OUTLINE.config.getBooleanValue()) {
            shape = VoxelShapes.fullCube();
            return;
        }
        shape = block.getState().getOutlineShape(client.world, block.getPos(), ShapeContext.of(entity));
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        updateShape(client, entity);
    }
}
