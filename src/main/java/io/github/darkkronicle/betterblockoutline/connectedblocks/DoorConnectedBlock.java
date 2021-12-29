package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/**
 * Connected block representing beds
 */
public class DoorConnectedBlock extends AbstractConnectedBlock {

    public DoorConnectedBlock(BlockPosState block) {
        this(block, new Vec3i(0, 0, 0));
    }

    public DoorConnectedBlock(BlockPosState block, Vec3i offset) {
        super(block, offset);
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        updateShape(client, entity);
        // Can only be top half or bottom half
        DoubleBlockHalf half = block.getState().get(DoorBlock.HALF);
        Direction otherDirection;
        if (half == DoubleBlockHalf.LOWER) {
            otherDirection = Direction.UP;
        } else {
            otherDirection = Direction.DOWN;
        }
        BlockPos otherPos = block.getPos().offset(otherDirection);
        BlockState otherState = client.world.getBlockState(otherPos);
        // Check to make sure it's a door and that it is not of the same half
        if (otherState.getBlock() instanceof DoorBlock && otherState.get(DoorBlock.HALF) != half) {
            AbstractConnectedBlock child = new DoorConnectedBlock(new BlockPosState(otherPos, otherState), otherDirection.getVector());
            child.updateShape(client, entity);
            children.add(child);
        }
    }
}
