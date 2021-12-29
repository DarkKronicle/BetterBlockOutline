package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

/**
 * Connected block that just contains a single blocks data. Used for any non-connected block.
 */
public class NormalConnectedBlock extends AbstractConnectedBlock {

    public NormalConnectedBlock(BlockPosState block) {
        super(block);
    }

    @Override
    public void populate(MinecraftClient client, Entity entity) {
        // Just setup this shape. No children.
        updateShape(client, entity);
    }
}
