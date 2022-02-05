package io.github.darkkronicle.betterblockoutline.colors;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.betterblockoutline.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;


public class ChromaColorModifier implements IColorModifier {

    private static String translate(String string) {
        return "betterblockoutline.config.chroma." + string;
    }

    private final SaveableConfig<ConfigDouble> duration = SaveableConfig.fromConfig("duration",
            new ConfigDouble(translate("duration"), 10, 0.01, 30, translate("info.duration")));

    private final SaveableConfig<ConfigDouble> offset = SaveableConfig.fromConfig("offset",
            new ConfigDouble(translate("offset"), 0, 0, 1, translate("info.offset")));

    @Override
    public Color4f getColor(BlockPosState block, Color4f original, long ms) {
        double loopTime = duration.config.getDoubleValue() * 1000;
        double percent = ((ms % loopTime) / loopTime + offset.config.getDoubleValue()) % 1;
        Color4f rgb = ColorUtil.getRainbow(percent);
        return new Color4f(rgb.r, rgb.g, rgb.b, original.a);
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    @Override
    public List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>();
        configs.add(duration);
        configs.add(offset);
        return configs;
    }

}
