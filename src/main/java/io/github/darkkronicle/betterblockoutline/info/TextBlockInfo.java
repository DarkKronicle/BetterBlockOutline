package io.github.darkkronicle.betterblockoutline.info;

import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class TextBlockInfo extends AbstractBlockInfo {

    public TextBlockInfo(Order order, String name, String translationName, String translationHover) {
        super(order, name, translationName, translationHover);
    }

    @Override
    public void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        // This is all done within inforenderer at once.
    }

    public abstract String[] getLines(AbstractConnectedBlock block);

    public static TextBlockInfo constructSimple(Order order, String name, String format, Property<?>... properties) {
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

    public static TextBlockInfo constructSimple(Order order, String name, Predicate<AbstractConnectedBlock> shouldRender, Function<AbstractConnectedBlock, String> lines) {
        return new TextBlockInfo(
                order,
                name,
                "betterblockoutline.blockinfo." + name,
                "betterblockoutline.blockinfo.info." + name
        ) {
            @Override
            public String[] getLines(AbstractConnectedBlock block) {
                return lines.apply(block).split("\n");
            }

            @Override
            public boolean shouldRender(AbstractConnectedBlock block) {
                return shouldRender.test(block);
            }
        };
    }

    public static void renderTextInfo(List<TextBlockInfo> texts, MinecraftClient client, MatrixStack matrices, AbstractConnectedBlock block) {
        List<String> lines = new ArrayList<>();
        for (TextBlockInfo text : texts) {
            if (text.isActive() && text.shouldRender(block)) {
                lines.addAll(Arrays.asList(text.getLines(block)));
            }
        }
        if (lines.size() == 0) {
            return;
        }
        BlockPos pos = block.getBlock().getPos();
        Vector3d vec = new Vector3d(pos.getX(), pos.getY(), pos.getZ());

        vec.add(new Vector3d(0.5, 0.5, 0.5));
        drawStringLines(matrices, client, lines, vec);

    }

    public static void drawString(MatrixStack matrices, MinecraftClient client, String line, Vector3d position) {
        drawStringLines(matrices, client, new String[]{line}, position);
    }

    public static void drawStringLines(MatrixStack matrices, MinecraftClient client, List<String> lines, Vector3d position) {
        drawStringLines(matrices, client, lines.toArray(new String[0]), position);
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        super.render(matrices, camera, entity, block);
        // So it doesn't cancel anything
        return false;
    }

    public static void drawStringLines(MatrixStack matrices, MinecraftClient client, String[] lines, Vector3d position) {
        Color4f textColor = ConfigStorage.Info.TEXT_COLOR.config.getColor();
        Color4f backgroundColor = ConfigStorage.Info.BACKGROUND_COLOR.config.getColor();
        double size = ConfigStorage.Info.TEXT_SIZE.config.getDoubleValue();
        double lineHeight = ConfigStorage.Info.LINE_HEIGHT.config.getDoubleValue();
        RenderingUtil.drawStringLines(matrices, client.textRenderer, lines, client.gameRenderer.getCamera(), position, (float) size, (float) lineHeight, false, textColor, backgroundColor);
    }
}
