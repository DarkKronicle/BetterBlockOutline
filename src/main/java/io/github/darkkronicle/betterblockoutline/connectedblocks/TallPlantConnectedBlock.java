package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmallDripleafBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/**
 * Connected block representing tall grass, tall fern, and flowers
 */
public class TallPlantConnectedBlock extends AbstractConnectedBlock {

    public TallPlantConnectedBlock(BlockPosState block) {
        this(block, new Vec3i(0, 0, 0));
    }

    public TallPlantConnectedBlock(BlockPosState block, Vec3i offset) {
        super(block, offset);
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        updateShape(client, entity);
        if (!isDouble(block.getState().getBlock())) {
            return;
        }
        DoubleBlockHalf half = block.getState().get(TallPlantBlock.HALF);
        Direction direction;
        if (half == DoubleBlockHalf.LOWER) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
        BlockPos otherPos = block.getPos().offset(direction);
        BlockState otherState = client.world.getBlockState(otherPos);
        if (isDouble(otherState.getBlock())) {
            AbstractConnectedBlock child = new TallPlantConnectedBlock(new BlockPosState(otherPos, otherState), direction.getVector());
            child.updateShape(client, entity);
            children.add(child);
        }
    }

    private static boolean isDouble(Block block) {
        return block instanceof TallPlantBlock && !(block instanceof SmallDripleafBlock);
    }
}
