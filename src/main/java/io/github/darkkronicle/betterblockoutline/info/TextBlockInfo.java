package io.github.darkkronicle.betterblockoutline.info;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public abstract class TextBlockInfo extends AbstractBlockInfo {

    private final MinecraftClient client;

    public TextBlockInfo(String name, String translationName, String translationHover) {
        super(name, translationName, translationHover);
        client = MinecraftClient.getInstance();
    }

    @Override
    public void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        String[] lines = getText(block).split("\n");
        BlockPos pos = block.getBlock().getPos();
        Vector3d vec = new Vector3d(pos.getX(), pos.getY(), pos.getZ());

        vec.add(new Vector3d(0.5, 0.5, 0.5));
        RenderingUtil.drawString(matrices, client.textRenderer, getText(block), client.gameRenderer.getCamera(), vec);
    }

    public abstract String getText(AbstractConnectedBlock block);

//    public static TextBlockInfo constructSimple() {
//
//    }
}
