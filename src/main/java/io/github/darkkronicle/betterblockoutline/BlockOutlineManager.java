package io.github.darkkronicle.betterblockoutline;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.connectedblocks.ConnectedBlockPopulator;
import io.github.darkkronicle.betterblockoutline.interfaces.IOverlayRenderer;
import io.github.darkkronicle.betterblockoutline.interfaces.IRenderable;
import io.github.darkkronicle.betterblockoutline.util.BlockPosState;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class BlockOutlineManager {

    private static final BlockOutlineManager INSTANCE = new BlockOutlineManager();
    private final MinecraftClient client;

    public static BlockOutlineManager getInstance() {
        return INSTANCE;
    }

    @Getter
    private final List<IOverlayRenderer> outlineRenderers = new ArrayList<>();

    @Getter
    private final List<IRenderable> renderers = new ArrayList<>();

    public void add(IOverlayRenderer renderer) {
        outlineRenderers.add(renderer);
    }

    public void add(IRenderable renderer) {
        renderers.add(renderer);
    }

    private BlockOutlineManager() {
        client = MinecraftClient.getInstance();
    }

    public void drawOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double camX, double camY, double camZ, BlockPos pos, BlockState state) {
        // Check if it is a double block
        Vector3d cam = new Vector3d(camX, camY, camZ);
        AbstractConnectedBlock block = ConnectedBlockPopulator.getInstance().getBlockData(new BlockPosState(pos, state), entity, client);
        for (IOverlayRenderer renderer : outlineRenderers) {
            renderer.render(matrices, cam, entity, block);
        }
    }

    public void render(MatrixStack matrices, Camera camera) {
        Vec3d vec = camera.getPos();
        Vector3d cam = new Vector3d(vec.getX(), vec.getY(), vec.getZ());
        for (IRenderable render : renderers) {
            render.render(matrices, cam, client.player);
        }
    }
}
