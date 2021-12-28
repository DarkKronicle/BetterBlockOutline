package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConnectedBlock {

    @Getter
    protected final BlockPosState block;
    protected final Vec3i offset;
    protected ArrayList<AbstractConnectedBlock> children = new ArrayList<>();

    public AbstractConnectedBlock(BlockPosState block) {
        this(block, new Vec3i(0, 0, 0));
    }

    public AbstractConnectedBlock(BlockPosState block, Vec3i offset) {
        this.block = block;
        this.offset = offset;
    }

    public Vec3i getOffset() {
        return offset;
    }

    public abstract VoxelShape getShape();

    public abstract void updateShape(MinecraftClient client, Entity entity);

    public abstract void populate(MinecraftClient client, Entity entity);

    public List<AbstractConnectedBlock> getChildren() {
        return children;
    }

}
