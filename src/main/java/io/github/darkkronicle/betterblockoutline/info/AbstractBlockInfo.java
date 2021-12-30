package io.github.darkkronicle.betterblockoutline.info;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.HotkeyCustomName;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.Hotkeys;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public abstract class AbstractBlockInfo implements IOverlayRenderer, Comparable<AbstractBlockInfo> {

    @AllArgsConstructor
    public enum Order {
        ALL(5),
        BLOCK(3),
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

    @Getter
    private final SaveableConfig<ConfigBoolean> active;

    @Getter
    private final SaveableConfig<ConfigHotkey> activeKey;

    @Getter
    private final Order order;

    public AbstractBlockInfo(Order order, String name, String translationName, String translationHover) {
        this.order = order;
        active = SaveableConfig.fromConfig(name, new ConfigBoolean(StringUtils.translate(translationName), false, StringUtils.translate(translationHover)));
        activeKey = SaveableConfig.fromConfig(name, new HotkeyCustomName("betterblockoutline.blockinfo.hotkeyname", "", KeybindSettings.MODIFIER_INGAME, "betterblockoutline.blockinfo.info.hotkeyname", translationName));
    }

    public abstract boolean shouldRender(AbstractConnectedBlock block);

    public abstract void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block);

    public boolean isActive() {
        return active.config.getBooleanValue();
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (isActive() && shouldRender(block)) {
            renderInfo(matrices, camera, entity, block);
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull AbstractBlockInfo o) {
        return new OrderComparator().compare(this.getOrder(), o.getOrder());
    }
}
