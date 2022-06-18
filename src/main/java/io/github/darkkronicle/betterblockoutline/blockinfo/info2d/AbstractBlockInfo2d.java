package io.github.darkkronicle.betterblockoutline.blockinfo.info2d;

import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.util.ColorUtil;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import io.github.darkkronicle.darkkore.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.state.property.Property;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractBlockInfo2d extends AbstractBlockInfo {

    public AbstractBlockInfo2d(Order order, String name, String translationName, String translationHover) {
        super(order, name, translationName, translationHover);
    }

    public abstract Optional<List<String>> getLines(AbstractConnectedBlock block);

    public static AbstractBlockInfo2d constructSimple(Order order, String name, String format, Property<?>... properties) {
        return constructSimple(order, name, (block) -> {
            for (Property<?> property : properties) {
                if (block.getBlock().getState().getOrEmpty(property).isPresent()) {
                    return true;
                }
            }
            return false;
        }, (block) -> {
            for (Property<?> property : properties) {
                Optional<?> prop = block.getBlock().getState().getOrEmpty(property);
                if (prop.isPresent()) {
                    return format.formatted(prop.get().toString());
                }
            }
            return "";
        });
    }

    public static AbstractBlockInfo2d constructSimple(Order order, String name, Predicate<AbstractConnectedBlock> shouldRender, Function<AbstractConnectedBlock, String> lines) {
        return new AbstractBlockInfo2d(
                order,
                name,
                "betterblockoutline.blockinfo2d." + name,
                "betterblockoutline.blockinfo2d.info." + name
        ) {
            @Override
            public Optional<List<String>> getLines(AbstractConnectedBlock block) {
                return Optional.of(Arrays.asList(lines.apply(block).split("\n")));
            }

            @Override
            public boolean shouldRender(AbstractConnectedBlock block) {
                return shouldRender.test(block);
            }
        };
    }

    public static void drawString(MatrixStack matrices, MinecraftClient client, String line, Vector3d position) {
        drawStringLines(matrices, client, new String[]{line}, position);
    }

    public static void drawStringLines(MatrixStack matrices, MinecraftClient client, List<String> lines, Vector3d position) {
        drawStringLines(matrices, client, lines.toArray(new String[0]), position);
    }

    public static void drawStringLines(MatrixStack matrices, MinecraftClient client, String[] lines, Vector3d position) {
        Color textColor = ConfigStorage.getBlockInfo2d().getTextColor().getValue();
        Color backgroundColor = ConfigStorage.getBlockInfo2d().getBackgroundColor().getValue();
        double size = ConfigStorage.getBlockInfo2d().getTextSize().getValue();
        double lineHeight = ConfigStorage.getBlockInfo2d().getLineHeight().getValue();
        RenderingUtil.drawStringLines(matrices, client.textRenderer, lines, client.gameRenderer.getCamera(), position, (float) size, (float) lineHeight, false, textColor, backgroundColor);
    }
}
