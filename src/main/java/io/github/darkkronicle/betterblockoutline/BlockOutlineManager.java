package io.github.darkkronicle.betterblockoutline;

import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.ConnectType;
import io.github.darkkronicle.betterblockoutline.connectedblocks.ConnectedBlockPopulator;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BasicOutlineRenderer;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockOutlineManager {

    private static final BlockOutlineManager INSTANCE = new BlockOutlineManager();
    private final MinecraftClient client;

    public static BlockOutlineManager getInstance() {
        return INSTANCE;
    }

    @Setter
    @Getter
    private IOverlayRenderer renderer;

    private BlockOutlineManager() {
        // TODO add some more renderers. Could be fun.
        renderer = new BasicOutlineRenderer();
        client = MinecraftClient.getInstance();
    }

    private List<BlockPosState> getBlocks(BlockPos pos, BlockState state) {
        List<BlockPosState> blocks = new ArrayList<>();
        blocks.add(new BlockPosState(pos, state));
        if (ConfigStorage.General.CONNECT_TYPE.config.getOptionListValue() == ConnectType.NONE) {
            return blocks;
        }
        if (state.getBlock() instanceof BedBlock) {

        } else if (state.getBlock() instanceof ChestBlock) {
            ChestType type = state.get(ChestBlock.CHEST_TYPE);
            if (type != ChestType.SINGLE) {

            }
        }
        return blocks;
    }

    public void drawOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double camX, double camY, double camZ, BlockPos pos, BlockState state) {
        // Check if it is a double block
        renderer.render(matrices, new Vector3d(camX, camY, camZ), entity, ConnectedBlockPopulator.getInstance().getBlockData(new BlockPosState(pos, state), entity, client));
    }

}
