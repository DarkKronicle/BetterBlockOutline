package io.github.darkkronicle.betterblockoutline.renderers;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.connectedblocks.NormalConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IRenderable;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class PersistentOutlineRenderer implements IRenderable {

    private final static PersistentOutlineRenderer INSTANCE = new PersistentOutlineRenderer();

    private final BasicOutlineRenderer basic;
    private final MinecraftClient client;

    private final List<BlockPosState> persistentBlocks = new ArrayList<>();

    public static PersistentOutlineRenderer getInstance() {
        return INSTANCE;
    }

    private PersistentOutlineRenderer() {
        this.basic = new BasicOutlineRenderer();
        this.client = MinecraftClient.getInstance();
    }

    public void addPos(BlockPos pos) {
        // Clean :)
        removePos(pos);
        persistentBlocks.add(new BlockPosState(pos, client.world.getBlockState(pos)));
    }

    public void removeIfExistsElseAdd(BlockPos pos) {
        if (removePos(pos)) {
            return;
        }
        addPos(pos);
    }

    public boolean contains(BlockPos pos) {
        return persistentBlocks.stream().anyMatch(blockPosState -> blockPosState.getPos().equals(pos));
    }

    public boolean removePos(BlockPos pos) {
        return persistentBlocks.removeIf(blockPosState -> blockPosState.getPos().equals(pos));
    }

    public void clear() {
        persistentBlocks.clear();
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity) {
        for (BlockPosState state : persistentBlocks) {
            AbstractConnectedBlock connected = new NormalConnectedBlock(state);
            connected.updateShape(client, entity);
            basic.render(matrices, camera, entity, connected);
        }
        return true;
    }

    public boolean toggleFromPlayer() {
        HitResult result = client.crosshairTarget;
        if (result == null || result.getType() != HitResult.Type.BLOCK) {
            return false;
        }
        removeIfExistsElseAdd(((BlockHitResult) result).getBlockPos());
        return true;
    }
}
