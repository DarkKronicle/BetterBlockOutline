package io.github.darkkronicle.betterblockoutline.info;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import net.minecraft.state.property.Properties;

public class NoteblockInfo extends TextBlockInfo {

    private final static String[] NOTES = new String[]{"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F"};

    public NoteblockInfo() {
        super(Order.SPECIFIC, "noteblockinfo", "betterblockoutline.blockinfo.noteblockinfo", "betterblockoutline.blockinfo.info.noteblockinfo");
    }

    @Override
    public boolean shouldRender(AbstractConnectedBlock block) {
        return block.getBlock().getState().getOrEmpty(Properties.INSTRUMENT).isPresent();
    }

    @Override
    public String[] getLines(AbstractConnectedBlock block) {
        BlockState state = block.getBlock().getState();
        Instrument instrument = state.get(Properties.INSTRUMENT);
        int note = state.get(Properties.NOTE);
    }

    public static String getNote(int note) {

    }

}
