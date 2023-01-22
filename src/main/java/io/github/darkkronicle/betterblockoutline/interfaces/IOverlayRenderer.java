package io.github.darkkronicle.betterblockoutline.interfaces;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Vector3d;

public interface IOverlayRenderer {

    boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block);

}
