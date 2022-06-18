package io.github.darkkronicle.betterblockoutline.colors;

import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.config.options.ColorOption;
import io.github.darkkronicle.darkkore.config.options.DoubleOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;

import java.util.ArrayList;
import java.util.List;

public class ToolColorModifier implements IColorModifier {

    private final BooleanOption onSuitable = new BooleanOption("onSuitable",
            "betterblockoutline.config.tool.onsuitable", "betterblockoutline.config.tool.info.onsuitable", true);

    private final ColorOption tint = new ColorOption("tint",
            "betterblockoutline.config.tool.color", "betterblockoutline.config.tool.info.color", new Color(255, 255, 255, 255));

    private final DoubleOption percent = new DoubleOption("percent",
            "betterblockoutline.config.tool.percent", "betterblockoutline.config.tool.info.percent", 1, 0, 1);


    @Override
    public Color getColor(BlockPosState block, Color original, long ms) {
        Item holding = MinecraftClient.getInstance().player.getInventory().getMainHandStack().getItem();
        if (!(holding instanceof MiningToolItem)) {
            return original;
        }
        MiningToolItem mining = (MiningToolItem) holding;
        boolean suitable = mining.isSuitableFor(block.getState());
        if (suitable != onSuitable.getValue()) {
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
        float r = invPercent * original.red() + percent * tintColor.red();
        float g = invPercent * original.green() + percent * tintColor.green();
        float b = invPercent * original.blue() + percent * tintColor.blue();
        float a = invPercent * original.alpha() + percent * tintColor.alpha();
        return new Color((int) r, (int) g, (int) b, (int) a);
    }

    @Override
    public List<Option<?>> getOptions() {
        List<Option<?>> configs = new ArrayList<>();
        configs.add(onSuitable);
        configs.add(tint);
        configs.add(percent);
        return configs;
    }

    @Override
    public Integer getOrder() {
        return 6;
    }
}
