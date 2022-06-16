package io.github.darkkronicle.betterblockoutline.blockinfo;

import io.github.darkkronicle.betterblockoutline.config.hotkeys.HotkeyCustomName;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractBlockInfo implements Comparable<AbstractBlockInfo> {

    /**
     * An enum that describes the order of rendering
     */
    @AllArgsConstructor
    public enum Order {
        /**
         * The renderer is on everything
         */
        ALL(5),

        /**
         * The renderer appears for all subsets of one block
         */
        BLOCK(3),

        /**
         * The renderer is for a very specific instance of block data
         */
        SPECIFIC(0)
        ;
        @Getter
        private final Integer order;
    }

    public static class OrderComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.getOrder().compareTo(o2.getOrder());
        }
    }

    /**
     * {@link SaveableConfig} for a {@link BooleanOption} that shows if the renderer is active.
     */
    @Getter
    private final BooleanOption active;

    /**
     * {@link SaveableConfig} for a {@link HotkeySettingsOption} that will toggle the renderer active.
     */
    @Getter
    private final HotkeySettingsOption activeKey;

    @Getter
    private final Order order;

    /**
     *
     * @param order {@link Order} for which it should be rendered
     * @param name The configuration name for the renderer
     * @param translationName The translation key for the name of the renderer
     * @param translationHover The translation key for hover information of the renderer
     */
    public AbstractBlockInfo(Order order, String name, String translationName, String translationHover) {
        this.order = order;
        active = new BooleanOption(name, translationName, translationHover, false);
        activeKey = new HotkeyCustomName(
                name, "betterblockoutline.blockinfo2d.hotkeyname", "betterblockoutline.blockinfo2d.info.hotkeyname",
                new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault())
        );
    }

    /**
     * Describes whether the renderer should render information. This acts as a predicate for the
     * default implementation of the render method.
     * @param block {@link AbstractConnectedBlock} containing information about the currently hovered block
     * @return If it should render
     */
    public abstract boolean shouldRender(AbstractConnectedBlock block);

    /**
     * If the renderer is active and it should be checked. This by default returns a configuration option
     * and is configurable by the user.
     * @return Active
     */
    public boolean isActive() {
        return active.getValue();
    }

    @Override
    public int compareTo(@NotNull AbstractBlockInfo o) {
        return new OrderComparator().compare(this.getOrder(), o.getOrder());
    }
}
