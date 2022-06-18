package io.github.darkkronicle.betterblockoutline.colors;

import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.betterblockoutline.util.ColorUtil;
import io.github.darkkronicle.darkkore.config.options.DoubleOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.util.Color;

import java.util.ArrayList;
import java.util.List;


public class ChromaColorModifier implements IColorModifier {

    private static String translate(String string) {
        return "betterblockoutline.config.chroma." + string;
    }

    private final DoubleOption duration = new DoubleOption("duration",
            translate("duration"), translate("info.duration"), 10, 0.01, 30);

    private final DoubleOption offset = new DoubleOption("offset",
            translate("offset"), translate("info.offset"), 0, 0, 1);

    @Override
    public Color getColor(BlockPosState block, Color original, long ms) {
        double loopTime = duration.getValue() * 1000;
        double percent = ((ms % loopTime) / loopTime + offset.getValue()) % 1;
        Color rgb = ColorUtil.getRainbow(percent);
        return new Color(rgb.red(), rgb.green(), rgb.blue(), original.alpha());
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    @Override
    public List<Option<?>> getOptions() {
        List<Option<?>> configs = new ArrayList<>();
        configs.add(duration);
        configs.add(offset);
        return configs;
    }

}
