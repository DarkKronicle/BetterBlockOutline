package io.github.darkkronicle.betterblockoutline.blockinfo;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.HotkeyCustomName;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

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
     * {@link SaveableConfig} for a {@link ConfigBoolean} that shows if the renderer is active.
     */
    @Getter
    private final SaveableConfig<ConfigBoolean> active;

    /**
     * {@link SaveableConfig} for a {@link ConfigHotkey} that will toggle the renderer active.
     */
    @Getter
    private final SaveableConfig<ConfigHotkey> activeKey;

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
        active = SaveableConfig.fromConfig(name, new ConfigBoolean(StringUtils.translate(translationName), false, StringUtils.translate(translationHover)));
        activeKey = SaveableConfig.fromConfig(name, new HotkeyCustomName("betterblockoutline.blockinfo2d.hotkeyname", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.blockinfo2d.info.hotkeyname", translationName));
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
        return active.config.getBooleanValue();
    }

    @Override
    public int compareTo(@NotNull AbstractBlockInfo o) {
        return new OrderComparator().compare(this.getOrder(), o.getOrder());
    }
}
