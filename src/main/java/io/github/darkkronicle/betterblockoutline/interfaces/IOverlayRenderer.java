package io.github.darkkronicle.betterblockoutline.interfaces;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;

import java.util.List;

public interface IOverlayRenderer {

    void render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block);

}
