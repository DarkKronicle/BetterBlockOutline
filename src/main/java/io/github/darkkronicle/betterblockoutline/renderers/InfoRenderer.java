package io.github.darkkronicle.betterblockoutline.renderers;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.info.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.info.NoteblockInfo;
import io.github.darkkronicle.betterblockoutline.info.RedstoneInfo;
import io.github.darkkronicle.betterblockoutline.info.TextBlockInfo;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import lombok.Getter;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InfoRenderer implements IOverlayRenderer {

    private final static InfoRenderer INSTANCE = new InfoRenderer();

    private final MinecraftClient client;
    @Getter
    private final List<AbstractBlockInfo> renderers = new ArrayList<>();

    @Getter
    private final List<TextBlockInfo> textRenderers = new ArrayList<>();

    @Getter
    private final List<AbstractBlockInfo> otherRenderers = new ArrayList<>();

    public static InfoRenderer getInstance() {
        return INSTANCE;
    }

    public void add(AbstractBlockInfo info) {
        renderers.add(info);
        if (info instanceof TextBlockInfo) {
            textRenderers.add((TextBlockInfo) info);
        } else {
            otherRenderers.add(info);
        }
    }

    public void setup() {
        add(new RedstoneInfo());
        add(new NoteblockInfo());
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.ALL, "coordinatestext", (block) -> true, (block) -> {
            BlockPos pos = block.getBlock().getPos();
            return "X: " + pos.getX() + "\nY: " + pos.getY() + "\nZ: " + pos.getZ();
        }));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "facingtext", "Facing: %s", Properties.FACING, Properties.HOPPER_FACING, Properties.HORIZONTAL_FACING));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "blocklevel", "Level: %s", Properties.LEVEL_3, Properties.LEVEL_8));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "waterloggedtext", "Waterlogged: %s", Properties.WATERLOGGED));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "opentext", "Open: %s", Properties.OPEN));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "persistent", "Persistent: %s", Properties.PERSISTENT));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "distancetext", "Distance: %s", Properties.DISTANCE_0_7, Properties.DISTANCE_1_7));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "layerstext", "Layers: %s", Properties.LAYERS));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "dripleaftilttext", "Tilt: %s", Properties.TILT));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "tilttext", "Charges: %s", Properties.CHARGES));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "bitestext", "Bites: %s", Properties.BITES));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "agetext", "Age: %s", Properties.AGE_1, Properties.AGE_2, Properties.AGE_3, Properties.AGE_5, Properties.AGE_7, Properties.AGE_15, Properties.AGE_25));
        add(TextBlockInfo.constructSimple(AbstractBlockInfo.Order.BLOCK, "beetext", (block) ->
                block.getBlock().getState().getOrEmpty(Properties.HONEY_LEVEL).isPresent() ||
                block.getBlock().getState().getOrEmpty(Properties.LEVEL_15).isPresent()
        , (block) -> {
                Optional<Integer> honey = block.getBlock().getState().getOrEmpty(Properties.HONEY_LEVEL);
                Optional<Integer> level = block.getBlock().getState().getOrEmpty(Properties.LEVEL_15);
                List<String> text = new ArrayList<>();
                honey.ifPresent(integer -> text.add("Honey: " + integer));
                level.ifPresent(integer -> text.add("Bee Level: " + integer));
                return String.join("\n", text);
        }));

        // Setup order so that generic ones get rendered last
        Collections.sort(renderers);
    }

    public List<ConfigHotkey> getHotkeys() {
        List<ConfigHotkey> keys = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            keys.add(info.getActiveKey().config);
        }
        return keys;
    }

    public List<SaveableConfig<? extends IConfigBase>> getHotkeyConfigs() {
        List<SaveableConfig<? extends IConfigBase>> keys = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            keys.add(info.getActiveKey());
        }
        return keys;
    }

    public List<SaveableConfig<? extends IConfigBase>> getActiveConfigs() {
        List<SaveableConfig<? extends IConfigBase>> active = new ArrayList<>();
        for (AbstractBlockInfo info : getRenderers()) {
            active.add(info.getActive());
        }
        return active;    }

    private InfoRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (!ConfigStorage.Info.ACTIVE.config.getBooleanValue()) {
            return false;
        }
        TextBlockInfo.renderTextInfo(textRenderers, client, matrices, block);
        for (AbstractBlockInfo renderer : otherRenderers) {
            if (renderer.render(matrices, camera, entity, block)) {
                return true;
            }
        }
        return false;
    }
}
