package io.github.darkkronicle.betterblockoutline.util;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.util.Color4f;
import lombok.experimental.UtilityClass;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

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

    /**
     * Gets the camera offset from a position
     * @param camera Camera position
     * @param pos Position to get difference
     * @return Difference
     */
    public Vector3d getCameraOffset(Vector3d camera, BlockPos pos) {
        double xDif = (double) pos.getX() - camera.x;
        double yDif = (double) pos.getY() - camera.y;
        double zDif = (double) pos.getZ() - camera.z;
        return new Vector3d(xDif, yDif, zDif);
    }

    /**
     * Gets the normal line from a starting and ending point.
     * @param start Starting point
     * @param end Ending point
     * @return Normal line
     */
    public Vector3f getNormalAngle(Vector3f start, Vector3f end) {
        float xLength = end.x - start.x;
        float yLength = end.y - start.y;
        float zLength = end.z - start.z;
        float distance = (float) Math.sqrt(xLength * xLength + yLength * yLength + zLength * zLength);
        xLength /= distance;
        yLength /= distance;
        zLength /= distance;
        return new Vector3f(xLength, yLength, zLength);
    }

    /**
     * Draw's a line and adds the camera position difference to the render.
     *
     * Rendering system should already be setup
     * @param entry Matrix entry
     * @param buffer Buffer builder that is already setup
     * @param camDif The position of render minus camera
     * @param start Starting point
     * @param end Ending point
     * @param color Color to render
     */
    public void drawLine(MatrixStack.Entry entry, BufferBuilder buffer, Vector3d camDif, Vector3f start, Vector3f end, Color4f color) {
        Vector3f startRaw = new Vector3f(start.x + camDif.x, start.y + camDif.y, start.z + camDif.z);
        Vector3f endRaw = new Vector3f(end.x + camDif.x, end.y + camDif.y, end.z + camDif.z);
        drawLine(entry, buffer, startRaw, endRaw, color);
    }

    /**
     * This method doesn't do any of the {@link RenderSystem} setting up. Should be setup before call.
     * @param entry Matrix entry
     * @param buffer Buffer builder that is already setup
     * @param start Starting point
     * @param end Ending point
     * @param color Color to render
     */
    public void drawLine(MatrixStack.Entry entry, BufferBuilder buffer, Vector3f start, Vector3f end, Color4f color) {
        Vector3f normal = RenderingUtil.getNormalAngle(start, end);

        buffer.vertex(
                entry.getPositionMatrix(), start.x, start.y, start.z
        ).color(color.r, color.g, color.b, color.a).normal(entry.getNormalMatrix(), normal.x, normal.y, normal.z).next();

        buffer.vertex(
                entry.getPositionMatrix(), end.x, end.y, end.z
        ).color(color.r, color.g, color.b, color.a).normal(entry.getNormalMatrix(), normal.x, normal.y, normal.z).next();
    }

}
