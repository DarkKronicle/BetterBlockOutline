package io.github.darkkronicle.betterblockoutline.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierContext;
import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.ConnectType;
import io.github.darkkronicle.betterblockoutline.config.OutlineType;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import io.github.darkkronicle.darkkore.util.Color;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;

public class BasicOutlineRenderer implements IOverlayRenderer {

    public BasicOutlineRenderer() {
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        ConnectType type = ConfigStorage.getGeneral().getConnectType().getValue();
        if (type == ConnectType.NONE || type == ConnectType.BLOCKS || block.getChildren().size() == 0) {
            renderShape(matrices, camera, entity, block.getBlock(), block.getShape());
            if (type == ConnectType.BLOCKS) {
                for (AbstractConnectedBlock child : block.getChildren()) {
                    renderShape(matrices, camera, entity, child.getBlock(), child.getShape());
                }
            }
            return true;
        }

        if (type == ConnectType.SEAMLESS) {
            VoxelShape shape = block.getShape();
            for (AbstractConnectedBlock child : block.getChildren()) {
                Vec3i offset = child.getOffset();
                shape = VoxelShapes.union(shape, child.getShape().offset(offset.getX(), offset.getY(), offset.getZ()));
            }
            renderShape(matrices, camera, entity, block.getBlock(), shape);
        }
        matrices.push();
        return true;
    }

    public void renderShape(MatrixStack matrices, Vector3d camera, Entity entity, BlockPosState block, VoxelShape outline) {
        Vector3d camDif = RenderingUtil.getCameraOffset(camera, block.getPos());
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        // Setup rendering
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderingUtil.setDepth(!ConfigStorage.getGeneral().getSeeThrough().getValue()); // See through
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        // Allow glass and other translucent/transparent objects to render properly
        Color fillColor = ConfigStorage.getGeneral().getFillColor().getValue();
        fillColor = processColor(block, fillColor, ColorModifierContext.FILL);
        if (fillColor.alpha() > 0) {
            drawOutlineBoxes(tessellator, matrices, buffer, camDif, fillColor, outline);
        }

        Color lineColor = ConfigStorage.getGeneral().getOutlineColor().getValue();
        lineColor = processColor(block, lineColor, ColorModifierContext.OUTLINE);
        if (lineColor.alpha() > 0) {
            drawOutlineLines(tessellator, matrices, buffer, camDif, lineColor, outline);
        }

        RenderSystem.depthMask(true);
        RenderingUtil.setDepth(true);
        RenderSystem.enableCull();
    }

    /**
     * Draws boxes for an outline. Depth and blending should be set before this is called.
     */
    private void drawOutlineBoxes(Tessellator tessellator, MatrixStack matrices, BufferBuilder buffer, Vector3d camDif, Color color, VoxelShape outline) {
        MatrixStack.Entry entry = matrices.peek();

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        // Divide into each edge and draw all of them
        outline.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Fix Z fighting
            minX -= .001;
            minY -= .001;
            minZ -= .001;
            maxX += .001;
            maxY += .001;
            maxZ += .001;
            RenderingUtil.drawBox(entry, buffer, camDif, (float) minX, (float) minY, (float) minZ, (float) maxX, (float) maxY, (float) maxZ, color);
        });
        tessellator.draw();
    }

    /**
     * Renders an outline and checks for {@link OutlineType}. Will be handled correctly. Sets shader and smooth lines.
     * Before calling blend and depth should be set
     */
    private void drawOutlineLines(Tessellator tessellator, MatrixStack matrices, BufferBuilder buffer, Vector3d camDif, Color color, VoxelShape outline) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.lineWidth((float) ConfigStorage.getGeneral().getOutlineWidth().getValue().doubleValue());

        OutlineType type = ConfigStorage.getGeneral().getOutlineType().getValue();
        if (type == OutlineType.LINE) {
            buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        } else if (type == OutlineType.STRIP) {
            buffer.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.LINES);
        } else {
            // Redundancy sake
            buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        }
        drawOutlineLine(tessellator, matrices.peek(), buffer, camDif, color, outline);

        // Revert some changes
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    /**
     * Draws an outline. Setup should be done before this method is called.
     */
    private void drawOutlineLine(Tessellator tessellator, MatrixStack.Entry entry, BufferBuilder buffer, Vector3d camDif, Color color, VoxelShape outline) {
        outline.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Fix Z fighting
            minX -= .001;
            minY -= .001;
            minZ -= .001;
            maxX += .001;
            maxY += .001;
            maxZ += .001;
            RenderingUtil.drawLine(entry, buffer, camDif, new Vector3d(minX, minY, minZ), new Vector3d(maxX, maxY, maxZ), color);
        });
        tessellator.draw();
    }

    public Color processColor(BlockPosState block, Color color, ColorModifierContext type) {
        long ms = Util.getMeasuringTimeMs();
        for (ConfigColorModifier<?> mod : ConfigStorage.getInstance().getColorMods(type.getConfigValue())) {
            if (mod.getActive().getValue()) {
                color = mod.getColorModifier().getColor(block, color, ms);
            }
        }
        return color;
    }

}

