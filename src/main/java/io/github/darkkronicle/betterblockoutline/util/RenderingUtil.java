package io.github.darkkronicle.betterblockoutline.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.util.Color4f;
import lombok.experimental.UtilityClass;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

@UtilityClass
public class RenderingUtil {

    public void drawBox(MatrixStack.Entry entry, BufferBuilder buffer, Vector3d cameraOffset, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color4f color) {
        minX = minX + (float) cameraOffset.x;
        minY = minY + (float) cameraOffset.y;
        minZ = minZ + (float) cameraOffset.z;
        maxX = maxX + (float) cameraOffset.x;
        maxY = maxY + (float) cameraOffset.y;
        maxZ = maxZ + (float) cameraOffset.z;

        Matrix4f position = entry.getPositionMatrix();

        // West
        buffer.vertex(position, minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();

        // East
        buffer.vertex(position, maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();

        // North
        buffer.vertex(position, maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();

        // South
        buffer.vertex(position, minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();

        // Top
        buffer.vertex(position, minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();

        // Bottom
        buffer.vertex(position, maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(position, maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
    }

    public void setDepth(boolean depth) {
        if (depth) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
    }

    public void setupRenderSystem(boolean depth) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        RenderSystem.disableBlend();
        setDepth(depth);
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
    }

    public void revertRenderSystem() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

    public Vector3d getCameraOffset(Vector3d camera, BlockPos pos) {
        double xDif = (double) pos.getX() - camera.x;
        double yDif = (double) pos.getY() - camera.y;
        double zDif = (double) pos.getZ() - camera.z;
        return new Vector3d(xDif, yDif, zDif);
    }

}
