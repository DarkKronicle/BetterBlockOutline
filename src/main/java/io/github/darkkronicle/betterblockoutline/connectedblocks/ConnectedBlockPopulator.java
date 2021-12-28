package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import lombok.AllArgsConstructor;
import lombok.Value;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConnectedBlockPopulator {

    private final static ConnectedBlockPopulator INSTANCE = new ConnectedBlockPopulator();

    public static ConnectedBlockPopulator getInstance() {
        return INSTANCE;
    }

    private List<ConnectedBlockEntry> connectedBlocks;

    @AllArgsConstructor
    @Value
    private static class ConnectedBlockEntry {
        Predicate<BlockPosState> predicate;
        Function<BlockPosState, AbstractConnectedBlock> supplier;
    }

    private ConnectedBlockPopulator() {
        setup();
    }

    public void setup() {
        connectedBlocks = new ArrayList<>();
        connectedBlocks.add(new ConnectedBlockEntry(blockPosState -> blockPosState.getState().getBlock() instanceof ChestBlock, ChestConnectedBlock::new));
    }

    public AbstractConnectedBlock getBlockData(BlockPosState block, Entity entity, MinecraftClient client) {
        AbstractConnectedBlock connected = null;
        for (ConnectedBlockEntry connectedBlock : connectedBlocks) {
            if (connectedBlock.predicate.test(block)) {
                connected = connectedBlock.supplier.apply(block);
                break;
            }
        }
        if (connected == null) {
            connected = new NormalBlock(block);
        }
        connected.populate(client, entity);
        return connected;
    }

}
