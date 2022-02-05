package io.github.darkkronicle.betterblockoutline.blockinfo.info3d;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.util.ColorUtil;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import io.github.darkkronicle.betterblockoutline.util.Vector3f;
import io.github.darkkronicle.betterblockoutline.util.VectorPair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ObserverBlock;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DirectionArrow extends AbstractBlockInfo3d {

    // TODO read files containing this?
    @AllArgsConstructor
    public enum ArrowType implements IConfigOptionListEntry {
        LINE_ARROW("line", false, Util.make(() -> {
            List<VectorPair> lines = new ArrayList<>();
            lines.add(new VectorPair(new Vector3f(0, -.5f, 0), new Vector3f(0, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(0, .5f, 0), new Vector3f(-.1f, .2f, 0)));
            lines.add(new VectorPair(new Vector3f(0, .5f, 0), new Vector3f(0, .2f, -.1f)));
            lines.add(new VectorPair(new Vector3f(0, .5f, 0), new Vector3f(.1f, .2f, 0)));
            lines.add(new VectorPair(new Vector3f(0, .5f, 0), new Vector3f(0, .2f, .1f)));
            lines.add(new VectorPair(new Vector3f(-.1f, .2f, 0), new Vector3f(0, .2f, -.1f)));
            lines.add(new VectorPair(new Vector3f(0, .2f, -.1f), new Vector3f(.1f, .2f, 0)));
            lines.add(new VectorPair(new Vector3f(.1f, .2f, 0f), new Vector3f(0, .2f, .1f)));
            lines.add(new VectorPair(new Vector3f(0, .2f, .1f), new Vector3f(-.1f, .2f, 0)));
            return lines;
        })),
        PYRAMID("pyramid", false, Util.make(() -> {
            List<VectorPair> lines = new ArrayList<>();
            // Points inwards
            lines.add(new VectorPair(new Vector3f(-.3f, -.4f, 0f), new Vector3f(0, .4f, 0)));
            lines.add(new VectorPair(new Vector3f(.3f, -.4f, 0f), new Vector3f(0, .4f, 0)));
            lines.add(new VectorPair(new Vector3f(0, -.4f, -.3f), new Vector3f(0, .4f, 0)));
            lines.add(new VectorPair(new Vector3f(0, -.4f, .3f), new Vector3f(0, .4f, 0)));
            // Connections
            lines.add(new VectorPair(new Vector3f(-.3f, -.4f, 0), new Vector3f(0, -.4f, -.3f)));
            lines.add(new VectorPair(new Vector3f(0, -.4f, -.3f), new Vector3f(.3f, -.4f, 0)));
            lines.add(new VectorPair(new Vector3f(.3f, -.4f, 0), new Vector3f(0, -.4f, .3f)));
            lines.add(new VectorPair(new Vector3f(0, -.4f, .3f), new Vector3f(-.3f, -.4f, 0)));
            return lines;
        })),
        CHEVRON("chevron", false, Util.make(() -> {
            List<VectorPair> lines = new ArrayList<>();
            lines.add(new VectorPair(new Vector3f(-.3f, -.4f, 0), new Vector3f(0, .4f, 0)));
            lines.add(new VectorPair(new Vector3f(.3f, -.4f, 0), new Vector3f(0, .4f, 0)));
            return lines;
        })),
        ALL_SIDES("sides", true, Util.make(() -> {
            List<VectorPair> lines = new ArrayList<>();
            // -X side
            lines.add(new VectorPair(new Vector3f(-.508f, -.5f, 0), new Vector3f(-.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(-.508f, .3f, -.3f), new Vector3f(-.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(-.508f, .3f, .3f), new Vector3f(-.508f, .5f, 0)));

            // +X side
            lines.add(new VectorPair(new Vector3f(.508f, -.5f, 0), new Vector3f(.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(.508f, .3f, -.3f), new Vector3f(.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(.508f, .3f, .3f), new Vector3f(.508f, .5f, 0)));

            // -Z side
            lines.add(new VectorPair(new Vector3f(0f, -.5f, -.508f), new Vector3f(0, .5f, -.508f)));
            lines.add(new VectorPair(new Vector3f(-.3f, .3f, -.508f), new Vector3f(0, .5f, -.508f)));
            lines.add(new VectorPair(new Vector3f(.3f, .3f, -.508f), new Vector3f(0, .5f, -.508f)));

            // +Z side
            lines.add(new VectorPair(new Vector3f(0f, -.5f, .508f), new Vector3f(0, .5f, .508f)));
            lines.add(new VectorPair(new Vector3f(-.3f, .3f, .508f), new Vector3f(0, .5f, .508f)));
            lines.add(new VectorPair(new Vector3f(.3f, .3f, .508f), new Vector3f(0, .5f, .508f)));

            // +Y side
            lines.add(new VectorPair(new Vector3f(-.1f, .508f, -.1f), new Vector3f(-.1f, .508f, .1f)));
            lines.add(new VectorPair(new Vector3f(-.1f, .508f, .1f), new Vector3f(.1f, .508f, .1f)));
            lines.add(new VectorPair(new Vector3f(.1f, .508f, .1f), new Vector3f(.1f, .508f, -.1f)));
            lines.add(new VectorPair(new Vector3f(.1f, .508f, -.1f), new Vector3f(-.1f, .508f, -.1f)));

            // -Y side
            lines.add(new VectorPair(new Vector3f(-.3f, -.508f, -.3f), new Vector3f(-.3f, -.508f, .3f)));
            lines.add(new VectorPair(new Vector3f(-.3f, -.508f, .3f), new Vector3f(.3f, -.508f, .3f)));
            lines.add(new VectorPair(new Vector3f(.3f, -.508f, .3f), new Vector3f(.3f, -.508f, -.3f)));
            lines.add(new VectorPair(new Vector3f(.3f, -.508f, -.3f), new Vector3f(-.3f, -.508f, -.3f)));
            return lines;
        })),
        SIDE_ARROWS("side_arrows", true, Util.make(() -> {
            List<VectorPair> lines = new ArrayList<>();

            // -X side
            lines.add(new VectorPair(new Vector3f(-.508f, -.5f, 0), new Vector3f(-.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(-.508f, .3f, -.3f), new Vector3f(-.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(-.508f, .3f, .3f), new Vector3f(-.508f, .5f, 0)));

            // +X side
            lines.add(new VectorPair(new Vector3f(.508f, -.5f, 0), new Vector3f(.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(.508f, .3f, -.3f), new Vector3f(.508f, .5f, 0)));
            lines.add(new VectorPair(new Vector3f(.508f, .3f, .3f), new Vector3f(.508f, .5f, 0)));

            // -Z side
            lines.add(new VectorPair(new Vector3f(0f, -.5f, -.508f), new Vector3f(0, .5f, -.508f)));
            lines.add(new VectorPair(new Vector3f(-.3f, .3f, -.508f), new Vector3f(0, .5f, -.508f)));
            lines.add(new VectorPair(new Vector3f(.3f, .3f, -.508f), new Vector3f(0, .5f, -.508f)));

            // +Z side
            lines.add(new VectorPair(new Vector3f(0f, -.5f, .508f), new Vector3f(0, .5f, .508f)));
            lines.add(new VectorPair(new Vector3f(-.3f, .3f, .508f), new Vector3f(0, .5f, .508f)));
            lines.add(new VectorPair(new Vector3f(.3f, .3f, .508f), new Vector3f(0, .5f, .508f)));
            return lines;
        }))
        ;

        private final String configValue;
        @Getter
        private final boolean useDepthTest;
        @Getter
        private final List<VectorPair> vectors;

        @Override
        public String getStringValue() {
            return configValue;
        }

        @Override
        public String getDisplayName() {
            return StringUtils.translate("betterblockoutline.config.directionarrow.arrowtype." + configValue);
        }

        @Override
        public ArrowType cycle(boolean forward) {
            int index = ordinal();
            if (forward) {
                index++;
            } else {
                index--;
            }
            return values()[index % values().length];
        }

        @Override
        public ArrowType fromString(String value) {
            for (ArrowType type : values()) {
                if (type.getStringValue().equals(value)) {
                    return type;
                }
            }
            return LINE_ARROW;
        }
    }

    public DirectionArrow() {
        super(Order.BLOCK, "directionarrow", "betterblockoutline.blockinfo3d.directionarrow", "betterblockoutline.blockinfo3d.info.directionarrow");
    }

    private static Optional<Direction> getDirection(BlockState state) {
        Optional<Direction> direction = state.getOrEmpty(Properties.FACING);
        if (direction.isPresent()) {
            return direction;
        }
        direction = state.getOrEmpty(Properties.HOPPER_FACING);
        if (direction.isPresent()) {
            return direction;
        }
        direction = state.getOrEmpty(Properties.HORIZONTAL_FACING);
        if (direction.isPresent()) {
            return direction;
        }
        return state.getOrEmpty(Properties.VERTICAL_DIRECTION);
    }

    @Override
    public boolean shouldRender(AbstractConnectedBlock block) {
        return getDirection(block.getBlock().getState()).isPresent();
    }

    public static List<VectorPair> getArrow() {
        return ((ArrowType) ConfigStorage.BlockInfoDirectionArrow.ARROW_TYPE.config.getOptionListValue()).getVectors();
    }

    @Override
    public void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        Vector3d camDif = RenderingUtil.getCameraOffset(camera, block.getBlock().getPos());
        Direction direction = getDirection(block.getBlock().getState()).get();

        ArrowType arrow = (ArrowType) ConfigStorage.BlockInfoDirectionArrow.ARROW_TYPE.config.getOptionListValue();
        if (ConfigStorage.BlockInfoDirectionArrow.LOGICAL_DIRECTION.config.getBooleanValue()) {
            direction = applyLogicalDirection(direction, block);
        }


        List<VectorPair> lines = getArrow();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesShader);
        RenderSystem.lineWidth((float) ConfigStorage.BlockInfo3d.LINE_WIDTH.config.getDoubleValue());
        RenderingUtil.setDepth(arrow.isUseDepthTest());
        RenderSystem.disableCull();
        RenderUtils.setupBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        MatrixStack.Entry entry = matrices.peek();
        for (VectorPair line : lines) {
            line = rotate(direction, line);
            line = offset(line);
            RenderingUtil.drawLine(entry, buffer, camDif, line.getVectorOne(), line.getVectorTwo(), ColorUtil.fromInt(ConfigStorage.BlockInfo3d.LINE_COLOR.config.getIntegerValue()));
        }
        tessellator.draw();
        RenderingUtil.setDepth(true);
        RenderSystem.enableCull();

    }

    private static Direction applyLogicalDirection(Direction direction, AbstractConnectedBlock blockData) {
        BlockState state = blockData.getBlock().getState();
        Block block = state.getBlock();
        if (block instanceof AbstractRedstoneGateBlock || block instanceof ObserverBlock) {
            return direction.getOpposite();
        }
        return direction;
    }

    private static VectorPair rotate(Direction direction, VectorPair pair) {
        Quaternion quaternion = direction.getRotationQuaternion();
        return new VectorPair(pair.getVectorOne().rotate(quaternion), pair.getVectorTwo().rotate(quaternion));
    }

    private static VectorPair offset(VectorPair pair) {
        Vector3f one = pair.getVectorOne();
        Vector3f two = pair.getVectorTwo();
        Vector3f oneNew = new Vector3f(.5f + one.x, .5f + one.y, .5f + one.z);
        Vector3f twoNew = new Vector3f(.5f + two.x, .5f + two.y, .5f + two.z);
        return new VectorPair(oneNew, twoNew);
    }

}
