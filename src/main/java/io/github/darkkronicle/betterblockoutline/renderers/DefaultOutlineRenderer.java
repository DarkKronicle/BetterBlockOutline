package io.github.darkkronicle.betterblockoutline.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.OutlineType;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import io.github.darkkronicle.betterblockoutline.util.Vector3f;
import org.lwjgl.opengl.GL11;

public class DefaultOutlineRenderer implements IOverlayRenderer {

    private final MinecraftClient client;

    public DefaultOutlineRenderer() {
        client = MinecraftClient.getInstance();
    }

    @Override
    public void render(MatrixStack matrices, Vector3d camera, Entity entity, BlockPos pos, BlockState blockState) {
        VoxelShape outline = blockState.getOutlineShape(client.world, pos, ShapeContext.of(entity));

        Vector3d camDif = RenderingUtil.getCameraOffset(camera, pos);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Color4f fillColor = ConfigStorage.General.FILL_COLOR.config.getColor();
        if (fillColor.a > 0) {
            drawBox(tessellator, matrices, buffer, camDif, fillColor, outline);
        }

        Color4f lineColor = ConfigStorage.General.OUTLINE_COLOR.config.getColor();
        if (lineColor.a > 0) {
            drawLine(tessellator, matrices, buffer, camDif, lineColor, outline);
        }


    }

    private void drawBox(Tessellator tessellator, MatrixStack matrices, BufferBuilder buffer, Vector3d camDif, Color4f color, VoxelShape outline) {
        RenderingUtil.setDepth(!ConfigStorage.General.SEE_THROUGH.config.getBooleanValue());
        RenderSystem.disableCull();
        MatrixStack.Entry entry = matrices.peek();
        RenderUtils.color(1, 1, 1, color.a);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        outline.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            RenderingUtil.drawBox(entry, buffer, camDif, (float) minX, (float) minY, (float) minZ, (float) maxX, (float) maxY, (float) maxZ, color);
        });
        tessellator.draw();
        RenderingUtil.setDepth(true);
        RenderUtils.color(1, 1 ,1, 1);
        RenderSystem.enableCull();
    }

    private void drawLine(Tessellator tessellator, MatrixStack matrices, BufferBuilder buffer, Vector3d camDif, Color4f color, VoxelShape outline) {
        RenderingUtil.setupRenderSystem(!ConfigStorage.General.SEE_THROUGH.config.getBooleanValue());
        RenderSystem.lineWidth((float) ConfigStorage.General.OUTLINE_WIDTH.config.getDoubleValue());
        RenderUtils.color(1, 1, 1, color.a);

        OutlineType type = (OutlineType) ConfigStorage.General.OUTLINE_TYPE.config.getOptionListValue();
        if (type == OutlineType.LINE) {
            buffer.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        } else if (type == OutlineType.STRIP) {
            buffer.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        } else {
            // Redundancy sake
            buffer.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        }


        MatrixStack.Entry entry = matrices.peek();
        outline.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> {
            Vector3f min = new Vector3f(minX + camDif.x, minY + camDif.y, minZ + camDif.z);
            Vector3f max = new Vector3f(maxX + camDif.x, maxY + camDif.y, maxZ + camDif.z);

            buffer.vertex(
                    entry.getPositionMatrix(), min.x, min.y, min.z
            ).color(color.r, color.g, color.b, color.a).next();

            buffer.vertex(
                    entry.getPositionMatrix(), max.x, max.y, max.z
            ).color(color.r, color.g, color.b, color.a).next();
        });
        tessellator.draw();
        RenderingUtil.revertRenderSystem();
        RenderUtils.color(1, 1 ,1, 1);
    }

}

