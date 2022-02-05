package io.github.darkkronicle.betterblockoutline.colors;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.betterblockoutline.util.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;

import java.util.ArrayList;
import java.util.List;

public class ToolColorModifier implements IColorModifier {

    private final SaveableConfig<ConfigBoolean> onSuitable = SaveableConfig.fromConfig("onSuitable",
            new ConfigBoolean("betterblockoutline.config.tool.onsuitable", true, "betterblockoutline.config.tool.info.onsuitable"));

    private final SaveableConfig<ConfigColor> tint = SaveableConfig.fromConfig("tint",
            new ConfigColor("betterblockoutline.config.tool.color", "#FFFFFFFF", "betterblockoutline.config.tool.info.color"));

    private final SaveableConfig<ConfigDouble> percent = SaveableConfig.fromConfig("percent",
            new ConfigDouble("betterblockoutline.config.tool.percent", 1, 0, 1, "betterblockoutline.config.tool.info.percent"));


    @Override
    public Color4f getColor(BlockPosState block, Color4f original, long ms) {
        Item holding = MinecraftClient.getInstance().player.getInventory().getMainHandStack().getItem();
        if (!(holding instanceof MiningToolItem)) {
            return original;
        }
        MiningToolItem mining = (MiningToolItem) holding;
        boolean suitable = mining.isSuitableFor(block.getState());
        if (suitable != onSuitable.config.getBooleanValue()) {
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
        float r = invPercent * original.r + percent * tintColor.r;
        float g = invPercent * original.g + percent * tintColor.g;
        float b = invPercent * original.b + percent * tintColor.b;
        float a = invPercent * original.a + percent * tintColor.a;
        return new Color4f(r, g, b, a);
    }

    @Override
    public List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>();
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
