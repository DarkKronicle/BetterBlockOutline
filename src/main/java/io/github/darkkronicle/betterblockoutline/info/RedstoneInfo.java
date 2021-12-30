package io.github.darkkronicle.betterblockoutline.info;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.state.property.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RedstoneInfo extends TextBlockInfo {

    public RedstoneInfo() {
        super(
                Order.BLOCK,
                "redstoneinfo",
                "betterblockoutline.blockinfo.redstoneinfo",
                "betterblockoutline.blockinfo.info.redstoneinfo"
        );
    }

    @Override
    public boolean shouldRender(AbstractConnectedBlock block) {
        return block.getBlock().getState().getOrEmpty(Properties.POWER).isPresent() || block.getBlock().getState().getOrEmpty(Properties.POWERED).isPresent();
    }

    @Override
    public String[] getLines(AbstractConnectedBlock block) {
        BlockState state = block.getBlock().getState();
        List<String> lines = new ArrayList<>();

        Optional<Integer> power = state.getOrEmpty(Properties.POWER);
        lines.add(power.map(integer -> "Power: " + integer).orElseGet(() -> "Powered: " + state.get(Properties.POWERED).toString()));

        state.getOrEmpty(ComparatorBlock.MODE).ifPresent(mode -> lines.add("Mode: " + mode.asString()));
        state.getOrEmpty(DaylightDetectorBlock.INVERTED).ifPresent(inverted -> lines.add("Inverted: " + inverted));
        state.getOrEmpty(RepeaterBlock.LOCKED).ifPresent(locked -> lines.add("Locked: " + locked));
        state.getOrEmpty(RepeaterBlock.DELAY).ifPresent(delay -> lines.add("Delay: " + delay));

        return lines.toArray(new String[0]);
    }
}
