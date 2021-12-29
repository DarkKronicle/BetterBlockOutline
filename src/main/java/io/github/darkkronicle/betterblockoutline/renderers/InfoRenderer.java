package io.github.darkkronicle.betterblockoutline.renderers;

import fi.dy.masa.malilib.util.Color4f;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class InfoRenderer implements IOverlayRenderer {

    private final MinecraftClient client;

    public InfoRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public boolean render(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        BlockPos pos = block.getBlock().getPos();
        Vector3d vec = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
        vec.add(new Vector3d(0.5, 0.5, 0.5));
        String[] lines = (pos.getX() + "\n" + pos.getY() + "\n" + pos.getZ()).split("\n");
        RenderingUtil.drawStringLines(matrices, client.textRenderer, lines, client.gameRenderer.getCamera(), vec, .02f, 10, false, new Color4f(1, 1, 1, 1), new Color4f(0, 0, 0, 0));
        return true;
    }
}
