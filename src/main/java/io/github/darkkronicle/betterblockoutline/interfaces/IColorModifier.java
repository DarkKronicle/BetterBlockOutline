package io.github.darkkronicle.betterblockoutline.interfaces;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to modify the color of either the fill or the outline of block outline rendering.
 */
public interface IColorModifier extends Comparable<IColorModifier> {

    /**
     * Modifies an input color and returns a new one.
     * @param original Original color that was being used
     * @param ms Measuring time in milliseconds
     * @return A new color to render. This can be passed into other modifiers.
     */
    Color4f getColor(Color4f original, long ms);

    /**
     * Strips down the {@link SaveableConfig}'s into the base elemtn of {@link IConfigBase}.
     */
    default List<IConfigBase> getOptions() {
        List<IConfigBase> configs = new ArrayList<>();
        for (SaveableConfig<? extends IConfigBase> config : getSaveableConfigs()) {
            configs.add(config.config);
        }
        return configs;
    }

    /**
     * The list of options for this color modifier. These will be used in a configuration menu.
     * @return
     */
    List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs();

    /**
     * Returns lines that will be shown when hovered over.
     */
    default List<String> getHoverLines() {
        return new ArrayList<>();
    }

    /**
     * The order in which this modifier will be sorted. The smaller the number the higher the priority.
     */
    default Integer getOrder() {
        return 1;
    }

    @Override
    default int compareTo(IColorModifier other) {
        return getOrder().compareTo(other.getOrder());
    }

}
