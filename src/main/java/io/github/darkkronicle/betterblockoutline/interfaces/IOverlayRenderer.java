package io.github.darkkronicle.betterblockoutline.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface IOverlayRenderer {

    void render(MatrixStack matrices, Vector3d camera, Entity entity, BlockPos blockPosition, BlockState blockState);

}
