package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/**
 * Connected block representing chests
 */
public class ChestConnectedBlock extends AbstractConnectedBlock {

    public ChestConnectedBlock(BlockPosState block) {
        this(block, new Vec3i(0, 0, 0));
    }

    public ChestConnectedBlock(BlockPosState block, Vec3i offset) {
        super(block, offset);
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        updateShape(client, entity);
        ChestType type = block.getState().get(ChestBlock.CHEST_TYPE);
        if (type == ChestType.SINGLE) {
            return;
        }
        Direction facing = ChestBlock.getFacing(block.getState());
        BlockPos otherPos = block.getPos().offset(facing);
        BlockState other = client.world.getBlockState(otherPos);
        if (other.getBlock() instanceof ChestBlock) {
            AbstractConnectedBlock connected = new ChestConnectedBlock(new BlockPosState(otherPos, other), facing.getVector());
            connected.updateShape(client, entity);
            children.add(connected);
        }
    }

}
