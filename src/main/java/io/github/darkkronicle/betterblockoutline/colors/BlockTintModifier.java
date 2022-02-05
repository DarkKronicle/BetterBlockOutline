package io.github.darkkronicle.betterblockoutline.colors;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.betterblockoutline.util.ColorUtil;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BlockTintModifier implements IColorModifier {

    private final SaveableConfig<ConfigColor> tint = SaveableConfig.fromConfig("tint",
            new ConfigColor("betterblockoutline.config.tint.color", "#FFFFFFFF", "betterblockoutline.config.tint.info.color"));

    private final SaveableConfig<ConfigDouble> percent = SaveableConfig.fromConfig("percent",
            new ConfigDouble("betterblockoutline.config.tint.percent", 1, 0, 1, "betterblockoutline.config.tint.info.percent"));

    private final SaveableConfig<ConfigString> blockNameRegex = SaveableConfig.fromConfig("blockNameRegex",
            new ConfigString("betterblockoutline.config.tint.blocknameregex", "grass", "betterblockoutline.config.tint.info.blocknameregex"));

    private Pattern pattern;

    private String lastString = "";

    private void compilePattern() {
        lastString = blockNameRegex.config.getStringValue();
        pattern = Pattern.compile(lastString);
    }

    @Override
    public Color4f getColor(BlockPosState block, Color4f original, long ms) {
        String name = Registry.BLOCK.getId(block.getState().getBlock()).toString();
        if (!lastString.equals(blockNameRegex.config.getStringValue())) {
            compilePattern();
        }
        if (!pattern.matcher(name).find()) {
            return original;
        }
        float percent = (float) this.percent.config.getDoubleValue();
        float invPercent = 1 - percent;
        if (percent == 0) {
            return original;
        }
        Color4f tintColor = ColorUtil.fromInt(tint.config.getIntegerValue());
        if (percent == 1) {
            return tintColor;
        }
        float r = invPercent * original.r + percent * Math.min(tintColor.r, 1);
        float g = invPercent * original.g + percent * Math.min(tintColor.g, 1);
        float b = invPercent * original.b + percent * Math.min(tintColor.b, 1);
        float a = invPercent * original.a + percent * Math.min(tintColor.a, 1);
        return new Color4f(r, g, b, a);
    }

    @Override
    public List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>();
        configs.add(tint);
        configs.add(percent);
        configs.add(blockNameRegex);
        return configs;
    }

    @Override
    public Integer getOrder() {
        return 5;
    }
}
