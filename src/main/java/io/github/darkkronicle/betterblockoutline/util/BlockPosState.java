package io.github.darkkronicle.betterblockoutline.util;

import lombok.AllArgsConstructor;
import lombok.Value;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

@AllArgsConstructor
@Value
public class BlockPosState {
    BlockPos pos;
    BlockState state;
}
