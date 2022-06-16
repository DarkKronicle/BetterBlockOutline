package io.github.darkkronicle.betterblockoutline.interfaces;

import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.util.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to modify the color of either the fill or the outline of block outline rendering.
 */
public interface IColorModifier extends Comparable<IColorModifier> {

    /**
     * Modifies an input color and returns a new one.
     * @param block Block for the color
     * @param original Original color that was being used
     * @param ms Measuring time in milliseconds
     * @return A new color to render. This can be passed into other modifiers.
     */
    Color getColor(BlockPosState block, Color original, long ms);

    /**
     * The list of options for this color modifier. These will be used in a configuration menu.
     * @return
     */
    List<Option<?>> getOptions();

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
