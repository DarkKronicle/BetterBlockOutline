package io.github.darkkronicle.betterblockoutline.renderers;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.blockinfo.AbstractBlockInfo;
import io.github.darkkronicle.betterblockoutline.blockinfo.info2d.NoteblockInfo;
import io.github.darkkronicle.betterblockoutline.blockinfo.info2d.RedstoneInfo;
import io.github.darkkronicle.betterblockoutline.blockinfo.info2d.SignText;
import io.github.darkkronicle.betterblockoutline.blockinfo.info2d.AbstractBlockInfo2d;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockInfo2dRenderer implements IOverlayRenderer {

    private final static BlockInfo2dRenderer INSTANCE = new BlockInfo2dRenderer();

    private final MinecraftClient client;
    @Getter
    private final List<AbstractBlockInfo2d> renderers = new ArrayList<>();

    public static BlockInfo2dRenderer getInstance() {
        return INSTANCE;
    }

    public void add(AbstractBlockInfo2d info) {
        renderers.add(info);
    }

    public void setup() {
        add(new RedstoneInfo());
        add(new NoteblockInfo());
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.ALL, "coordinatestext", (block) -> true, (block) -> {
            BlockPos pos = block.getBlock().getPos();
            return "X: " + pos.getX() + "\nY: " + pos.getY() + "\nZ: " + pos.getZ();
        }));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "facingtext", "Facing: %s", Properties.FACING, Properties.HOPPER_FACING, Properties.HORIZONTAL_FACING));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "blocklevel", "Level: %s", Properties.LEVEL_3, Properties.LEVEL_8));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "waterloggedtext", "Waterlogged: %s", Properties.WATERLOGGED));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "opentext", "Open: %s", Properties.OPEN));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "persistent", "Persistent: %s", Properties.PERSISTENT));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "distancetext", "Distance: %s", Properties.DISTANCE_0_7, Properties.DISTANCE_1_7));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "layerstext", "Layers: %s", Properties.LAYERS));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "dripleaftilttext", "Tilt: %s", Properties.TILT));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "chargestext", "Charges: %s", Properties.CHARGES));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "bitestext", "Bites: %s", Properties.BITES));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "agetext", "Age: %s", Properties.AGE_1, Properties.AGE_2, Properties.AGE_3, Properties.AGE_5, Properties.AGE_7, Properties.AGE_15, Properties.AGE_25));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "beetext", "Honey Level: %s", Properties.HONEY_LEVEL));
        add(AbstractBlockInfo2d.constructSimple(AbstractBlockInfo.Order.BLOCK, "leveltext", "Level: %s", Properties.LEVEL_15));
        add(new SignText());

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
        return active;
    }

    private BlockInfo2dRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (!ConfigStorage.BlockInfo2d.ACTIVE.config.getBooleanValue()) {
            return false;
        }
        renderTextInfo(renderers, client, matrices, block);
        return false;
    }

    public static void renderTextInfo(List<AbstractBlockInfo2d> texts, MinecraftClient client, MatrixStack matrices, AbstractConnectedBlock block) {
        List<String> lines = new ArrayList<>();
        for (AbstractBlockInfo2d text : texts) {
            if (text.isActive() && text.shouldRender(block)) {
                text.getLines(block).ifPresent(lines::addAll);
            }
        }
        if (lines.size() == 0) {
            return;
        }
        BlockPos pos = block.getBlock().getPos();
        Vector3d vec = new Vector3d(pos.getX(), pos.getY(), pos.getZ());

        vec.add(new Vector3d(0.5, 0.5, 0.5));
        AbstractBlockInfo2d.drawStringLines(matrices, client, lines, vec);

    }
}
