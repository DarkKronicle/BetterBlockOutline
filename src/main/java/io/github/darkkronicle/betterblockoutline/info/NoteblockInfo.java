package io.github.darkkronicle.betterblockoutline.info;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.util.TextUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import net.minecraft.state.property.Properties;

import java.util.Map;

import static java.util.Map.entry;

public class NoteblockInfo extends TextBlockInfo {

    private final static String[] NOTES = new String[]{"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F"};
    private final static Map<Instrument, Integer> STARTING_OCTAVE = Map.ofEntries(
        entry(Instrument.BASS, 1),
        entry(Instrument.SNARE, 1),
        entry(Instrument.HAT, 1),
        entry(Instrument.BASEDRUM, 1),
        entry(Instrument.BELL, 5),
        entry(Instrument.FLUTE, 4),
        entry(Instrument.CHIME, 5),
        entry(Instrument.GUITAR, 2),
        entry(Instrument.XYLOPHONE, 5),
        entry(Instrument.IRON_XYLOPHONE, 3),
        entry(Instrument.COW_BELL, 4),
        entry(Instrument.DIDGERIDOO, 1),
        entry(Instrument.BIT, 3),
        entry(Instrument.BANJO, 3),
        entry(Instrument.PLING, 3),
        entry(Instrument.HARP, 3)
    );

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
        return new String[]{instrument.asString().replaceAll("_", " "), getNote(note, instrument)};
    }

    public static String getNote(int note, Instrument instrument) {
        String noteText = NOTES[note % 12];
        int octave = STARTING_OCTAVE.getOrDefault(instrument, 1) + (note / 12);
        return noteText + TextUtil.toSubscript(octave);
    }

}
