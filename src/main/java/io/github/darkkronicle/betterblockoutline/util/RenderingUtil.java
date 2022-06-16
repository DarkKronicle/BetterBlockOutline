package io.github.darkkronicle.betterblockoutline.util;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.darkkronicle.darkkore.util.Color;
import lombok.experimental.UtilityClass;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

@UtilityClass
public class RenderingUtil {

    public void drawBox(MatrixStack.Entry entry, BufferBuilder buffer, Vector3d cameraOffset, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color color) {
        minX = minX + (float) cameraOffset.x;
        minY = minY + (float) cameraOffset.y;
        minZ = minZ + (float) cameraOffset.z;
        maxX = maxX + (float) cameraOffset.x;
        maxY = maxY + (float) cameraOffset.y;
        maxZ = maxZ + (float) cameraOffset.z;

        Matrix4f position = entry.getPositionMatrix();
        float r = color.red() / 255f;
        float g = color.green() / 255f;
        float b = color.blue() / 255f;
        float a = color.alpha() / 255f;

        // West
        buffer.vertex(position, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, maxY, minZ).color(r, g, b, a).next();

        // East
        buffer.vertex(position, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, maxY, maxZ).color(r, g, b, a).next();

        // North
        buffer.vertex(position, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, maxY, minZ).color(r, g, b, a).next();

        // South
        buffer.vertex(position, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, maxY, maxZ).color(r, g, b, a).next();

        // Top
        buffer.vertex(position, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, maxY, minZ).color(r, g, b, a).next();

        // Bottom
        buffer.vertex(position, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(position, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(position, maxX, minY, minZ).color(r, g, b, a).next();
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
    public void drawLine(MatrixStack.Entry entry, BufferBuilder buffer, Vector3d camDif, Vector3f start, Vector3f end, Color color) {
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
    public void drawLine(MatrixStack.Entry entry, BufferBuilder buffer, Vector3f start, Vector3f end, Color color) {
        Vector3f normal = RenderingUtil.getNormalAngle(start, end);
        float r = color.red() / 255f;
        float g = color.green() / 255f;
        float b = color.blue() / 255f;
        float a = color.alpha() / 255f;

        buffer.vertex(
                entry.getPositionMatrix(), start.x, start.y, start.z
        ).color(r, g, b, a).normal(entry.getNormalMatrix(), normal.x, normal.y, normal.z).next();

        buffer.vertex(
                entry.getPositionMatrix(), end.x, end.y, end.z
        ).color(r, g, b, a).normal(entry.getNormalMatrix(), normal.x, normal.y, normal.z).next();
    }

    public void drawString(MatrixStack matrices, TextRenderer renderer, String string, Camera camera, Vector3d position) {
        drawString(matrices, renderer, string, camera, position, new Color(255, 255, 255, 255));
    }

    public void drawString(MatrixStack matrices, TextRenderer renderer, String string, Camera camera, Vector3d position, Color textColor) {
        drawString(matrices, renderer, string, camera, position, 0.02f, false, textColor);
    }

    public void drawString(MatrixStack matrices, TextRenderer renderer, String string, Camera camera, Vector3d position, float size, boolean depth, Color textColor) {
        drawString(matrices, renderer, string, camera, position, size, 10, depth, textColor, new Color(0, 0, 0, 0));
    }

    public void drawString(MatrixStack matrices, TextRenderer textRenderer, String string, Camera camera, Vector3d position, float size, float lineHeight, boolean depth, Color textColor, Color backgroundColor) {
        drawStringLines(matrices, textRenderer, new String[]{string}, camera, position, size, lineHeight, depth, textColor, backgroundColor);
    }

    public void drawStringLines(MatrixStack matrices, TextRenderer renderer, String[] lines, Camera camera, Vector3d position, float size, boolean depth, Color textColor) {
        drawStringLines(matrices, renderer, lines, camera, position, size, 10, depth, textColor, new Color(0, 0, 0, 0));
    }

    public void drawStringLines(MatrixStack matrices, TextRenderer textRenderer, String[] lines, Camera camera, Vector3d position, float size, float lineHeight, boolean depth, Color textColor, Color backgroundColor) {
        matrices.push();
        matrices.translate(position.x - camera.getPos().x, position.y - camera.getPos().y + 0.07f, position.z - camera.getPos().z);
        matrices.multiply(camera.getRotation());
        matrices.scale(-size, -size, size);
        setDepth(depth);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        float yOffset = ((float) (lines.length - 1) / 2) * lineHeight;
        matrices.translate(0, -yOffset, 0);
        for (String line : lines) {
            float xOffset = -1 * (textRenderer.getWidth(line) / 2.0f);
            textRenderer.draw(line, xOffset, 0.0f, textColor.color(), false, matrix4f, immediate, !depth, backgroundColor.color(), LightmapTextureManager.MAX_LIGHT_COORDINATE);
            matrices.translate(0, lineHeight, 0);
        }
        immediate.draw();
        RenderSystem.enableDepthTest();
        matrices.pop();
    }

}
