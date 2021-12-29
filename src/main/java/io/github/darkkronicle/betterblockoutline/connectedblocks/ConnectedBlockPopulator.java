package io.github.darkkronicle.betterblockoutline.connectedblocks;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import net.minecraft.block.BedBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility class to get links of blocks.
 */
public class ConnectedBlockPopulator {

    private final static ConnectedBlockPopulator INSTANCE = new ConnectedBlockPopulator();

    public static ConnectedBlockPopulator getInstance() {
        return INSTANCE;
    }

    @Getter
    private List<ConnectedBlockEntry> connectedBlocks;

    @AllArgsConstructor
    @Value
    private static class ConnectedBlockEntry {
        /**
         * Predicate to see if the supplier's information should be used.
         */
        Predicate<BlockPosState> predicate;

        /**
         * The consumer of {@link BlockPosState} and returns the {@link AbstractConnectedBlock} data.
         */
        Function<BlockPosState, AbstractConnectedBlock> supplier;
    }

    private ConnectedBlockPopulator() {
        setup();
    }

    /**
     * Sets up all the connected block entries.
     */
    public void setup() {
        connectedBlocks = new ArrayList<>();
        connectedBlocks.add(new ConnectedBlockEntry(blockPosState -> blockPosState.getState().getBlock() instanceof ChestBlock, ChestConnectedBlock::new));
        connectedBlocks.add(new ConnectedBlockEntry(blockPosState -> blockPosState.getState().getBlock() instanceof BedBlock, BedConnectedBlock::new));
        connectedBlocks.add(new ConnectedBlockEntry(blockPosState -> blockPosState.getState().getBlock() instanceof DoorBlock, DoorConnectedBlock::new));
        connectedBlocks.add(new ConnectedBlockEntry(blockPosState -> blockPosState.getState().getBlock() instanceof TallPlantBlock, TallPlantConnectedBlock::new));
        connectedBlocks.add(new ConnectedBlockEntry(blockPosState ->
                blockPosState.getState().getBlock() instanceof PistonBlock ||
                blockPosState.getState().getBlock() instanceof PistonHeadBlock,
                PistonConnectedBlock::new));
    }

    /**
     * Gets block data from a {@link BlockPosState}
     * @param block Block to get all neighbors and itself
     * @param entity {@link Entity} used to view the block
     * @param client Client
     * @return {@link AbstractConnectedBlock} containing the original block and all the links.
     */
    public AbstractConnectedBlock getBlockData(BlockPosState block, Entity entity, MinecraftClient client) {
        AbstractConnectedBlock connected = null;
        for (ConnectedBlockEntry connectedBlock : connectedBlocks) {
            if (connectedBlock.predicate.test(block)) {
                // Break when first found
                connected = connectedBlock.supplier.apply(block);
                break;
            }
        }
        if (connected == null) {
            // Setup just a single one
            connected = new NormalConnectedBlock(block);
        }
        connected.populate(client, entity);
        return connected;
    }

}
