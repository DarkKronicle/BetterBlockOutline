package io.github.darkkronicle.betterblockoutline;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.shape.VoxelShape;
import org.lwjgl.opengl.GL11;

public class BlockOutlineManager {

    private static final BlockOutlineManager INSTANCE = new BlockOutlineManager();

    private final MinecraftClient client;

    public static BlockOutlineManager getInstance() {
        return INSTANCE;
    }

    private BlockOutlineManager() {
        client = MinecraftClient.getInstance();
    }

    public void drawOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double camX, double camY, double camZ, BlockPos pos, BlockState state) {
        VoxelShape outline = state.getOutlineShape(client.world, pos, ShapeContext.of(entity));
        double xDif = (double) pos.getX() - camX;
        double yDif = (double) pos.getY() - camY;
        double zDif = (double) pos.getZ() - camZ;
        MatrixStack.Entry entry = matrices.peek();
        Vector3d cam = new Vector3d(xDif, yDif, zDif);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.lineWidth(1);
        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        outline.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> {
//            float xLength = (float) (maxX - minX);
//            float yLength = (float) (maxY - minY);
//            float zLength = (float) (maxZ - minZ);
//            float squared = (float) Math.sqrt(xLength * xLength + yLength * yLength + zLength * zLength);
//
//            vertexConsumer.vertex(entry.getPositionMatrix(), (float) (minX + xDif), (float) (minY + yDif), (float) (minZ + zDif)).color(0, 0, 0, 0.5f)
//                    .normal(entry.getNormalMatrix(), xLength / squared, yLength / squared, zLength / squared).next();
//
//            vertexConsumer.vertex(entry.getPositionMatrix(), (float) (maxX + xDif), (float) (maxY + yDif), (float) (maxZ + zDif)).color(0, 0, 0, 0.5f)
//                    .normal(entry.getNormalMatrix(), xLength, yLength, zLength).next();
            buffer.vertex(entry.getPositionMatrix(), (float) (minX + xDif), (float) (minY + yDif), (float) (minZ + zDif)).color(1, 1, 1, 1f).next();
            buffer.vertex(entry.getPositionMatrix(), (float) (maxX + xDif), (float) (maxY + yDif), (float) (maxZ + zDif)).color(1, 1, 1, 1f).next();
        });
        tessellator.draw();
//        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//        outline.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
//            drawBox(entry, buffer, cam, (float) minX, (float) minY, (float) minZ, (float) maxX, (float) maxY, (float) maxZ, new Color4f(0, 0, 0.1f, 0.25f));
//        });
//        tessellator.draw();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

    public static void drawBox(MatrixStack.Entry entry, BufferBuilder buffer, Vector3d cameraOffset, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color4f color) {
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

}
