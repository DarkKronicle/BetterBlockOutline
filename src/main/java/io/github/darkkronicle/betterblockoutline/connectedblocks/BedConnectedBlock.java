package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/**
 * Connected block representing beds
 */
public class BedConnectedBlock extends AbstractConnectedBlock {

    public BedConnectedBlock(BlockPosState block) {
        this(block, new Vec3i(0, 0, 0));
    }

    public BedConnectedBlock(BlockPosState block, Vec3i offset) {
        super(block, offset);
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        updateShape(client, entity);
        // Get the part
        BedPart part = block.getState().get(BedBlock.PART);
        Direction otherDirection = block.getState().get(HorizontalFacingBlock.FACING);
        if (part == BedPart.HEAD) {
            otherDirection = otherDirection.getOpposite();
        }
        // Find other position
        BlockPos otherPos = block.getPos().offset(otherDirection);
        BlockState other = client.world.getBlockState(otherPos);
        // Ensure that it is a bed
        // TODO add a check to make sure the direction is the same.
        if (other.getBlock() instanceof BedBlock && other.get(BedBlock.PART) != part) {
            AbstractConnectedBlock otherBed = new BedConnectedBlock(new BlockPosState(otherPos, other), otherDirection.getVector());
            otherBed.updateShape(client, entity);
            children.add(otherBed);
        }
    }

}
