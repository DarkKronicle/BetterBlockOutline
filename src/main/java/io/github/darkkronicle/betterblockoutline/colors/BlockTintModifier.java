package io.github.darkkronicle.betterblockoutline.colors;

import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.darkkore.config.options.ColorOption;
import io.github.darkkronicle.darkkore.config.options.DoubleOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.StringOption;
import io.github.darkkronicle.darkkore.util.Color;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BlockTintModifier implements IColorModifier {

    private final ColorOption tint = new ColorOption("tint",
            "betterblockoutline.config.tint.color", "betterblockoutline.config.tint.info.color", new Color(255, 255, 255, 255));

    private final DoubleOption percent = new DoubleOption("percent",
            "betterblockoutline.config.tint.percent", "betterblockoutline.config.tint.info.percent", 1, 0, 1);

    private final StringOption blockNameRegex = new StringOption("blockNameRegex",
            "betterblockoutline.config.tint.blocknameregex", "betterblockoutline.config.tint.info.blocknameregex", "grass");

    private Pattern pattern;

    private String lastString = "";

    private void compilePattern() {
        lastString = blockNameRegex.getValue();
        pattern = Pattern.compile(lastString);
    }

    @Override
    public Color getColor(BlockPosState block, Color original, long ms) {
        if (pattern == null) {
            return original;
        }
        String name = Registries.BLOCK.getId(block.getState().getBlock()).toString();
        if (!lastString.equals(blockNameRegex.getValue())) {
            compilePattern();
        }
        if (!pattern.matcher(name).find()) {
            return original;
        }
        float percent = (float) this.percent.getValue().doubleValue();
        float invPercent = 1 - percent;
        if (percent == 0) {
            return original;
        }
        Color tintColor = tint.getValue();
        if (percent == 1) {
            return tintColor;
        }
        float r = invPercent * original.red() + percent * Math.min(tintColor.red(), 255);
        float g = invPercent * original.green() + percent * Math.min(tintColor.green(), 255);
        float b = invPercent * original.blue() + percent * Math.min(tintColor.blue(), 255);
        float a = invPercent * original.alpha() + percent * Math.min(tintColor.alpha(), 255);
        return new Color((int) r, (int) g, (int) b, (int) a);
    }

    @Override
    public List<Option<?>> getOptions() {
        List<Option<?>> configs = new ArrayList<>();
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
