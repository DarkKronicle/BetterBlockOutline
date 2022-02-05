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

public class BlinkColorModifier implements IColorModifier {

    private static String translate(String string) {
        return "betterblockoutline.config.blink." + string;
    }

    private final SaveableConfig<ConfigDouble> duration = SaveableConfig.fromConfig("duration",
            new ConfigDouble(translate("duration"), 5, 0.5, 30, translate("info.duration")));

    private final SaveableConfig<ConfigDouble> offset = SaveableConfig.fromConfig("offset",
            new ConfigDouble(translate("offset"), 0, 0, 1, translate("info.duration")));


    @Override
    public Color4f getColor(BlockPosState block, Color4f original, long ms) {
        double loopTime = duration.config.getDoubleValue() * 1000;
        double percent = ((ms % loopTime) / loopTime + offset.config.getDoubleValue()) % 1;
        float alpha = ColorUtil.getBlink(percent, original.a);
        return new Color4f(original.r, original.g, original.b, alpha);
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    @Override
    public List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>();
        configs.add(duration);
        configs.add(offset);
        return configs;
    }
}
