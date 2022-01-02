package io.github.darkkronicle.betterblockoutline.util;

import lombok.AllArgsConstructor;
import lombok.Value;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

/**
 * An immutable class that holds a block position and the state of that block
 */
@AllArgsConstructor
@Value
public class BlockPosState {
    BlockPos pos;
    BlockState state;
}
