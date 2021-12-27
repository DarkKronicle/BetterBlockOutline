package io.github.darkkronicle.betterblockoutline.interfaces;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;

import java.util.ArrayList;
import java.util.List;

public interface IColorModifier extends Comparable<IColorModifier> {

    Color4f getColor(Color4f original, long ms);

    default List<IConfigBase> getOptions() {
        List<IConfigBase> configs = new ArrayList<>();
        for (SaveableConfig<? extends IConfigBase> config : getSaveableConfigs()) {
            configs.add(config.config);
        }
        return configs;
    }

    List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs();

    default List<String> getHoverLines() {
        return new ArrayList<>();
    }

    default Integer getOrder() {
        return 1;
    }

    @Override
    default int compareTo(IColorModifier other) {
        return getOrder().compareTo(other.getOrder());
    }

}
