package io.github.darkkronicle.betterblockoutline.interfaces;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Vector3d;

public interface IRenderable {
    boolean render(MatrixStack matrices, Vector3d camera, Entity entity);
}
