package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/**
 * Connected block representing pistons
 */
public class PistonConnectedBlock extends AbstractConnectedBlock {

    public PistonConnectedBlock(BlockPosState block) {
        this(block, new Vec3i(0, 0, 0));
    }

    public PistonConnectedBlock(BlockPosState block, Vec3i offset) {
        super(block, offset);
    }

    private void populateFromBlock(MinecraftClient client, Entity entity) {
        Direction facing = block.getState().get(PistonBlock.FACING);
        Direction opposite = facing.getOpposite();
        BlockPos otherPos = block.getPos().offset(opposite);
        BlockState otherState = client.world.getBlockState(otherPos);
        if (otherState.getBlock() instanceof PistonBlock && otherState.get(PistonBlock.FACING) == facing) {
            AbstractConnectedBlock child = new PistonConnectedBlock(new BlockPosState(otherPos, otherState), opposite.getVector());
            child.updateShape(client, entity);
            children.add(child);
        }
    }

    private void populateFromHead(MinecraftClient client, Entity entity) {
        Direction facing = block.getState().get(PistonBlock.FACING);
        // Invert since facing is the push direction
        BlockPos otherPos = block.getPos().offset(facing);
        BlockState otherState = client.world.getBlockState(otherPos);
        if (otherState.getBlock() instanceof PistonHeadBlock && otherState.get(PistonBlock.FACING) == facing) {
            AbstractConnectedBlock child = new PistonConnectedBlock(new BlockPosState(otherPos, otherState), facing.getVector());
            child.updateShape(client, entity);
            children.add(child);
        }
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        updateShape(client, entity);
        // A piston can either be a PistonHead or a regular Piston. Piston is the bottom block, PistonHead is the extension.
        if (block.getState().getBlock() instanceof PistonHeadBlock) {
            populateFromBlock(client, entity);
        } else if (block.getState().get(PistonBlock.EXTENDED)) {
            populateFromHead(client, entity);
        }
    }
}
