package io.github.darkkronicle.betterblockoutline.blockinfo.info3d;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.render.RenderUtils;
import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import io.github.darkkronicle.betterblockoutline.util.RenderingUtil;
import net.minecraft.block.InfestedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.SilverfishEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.util.math.Vec3d;

public class InfestedSilverfish extends AbstractBlockInfo3d {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private SilverfishEntity silverFish = null;
    private SilverfishCustomRenderer renderer = null;

    public InfestedSilverfish() {
        super(Order.SPECIFIC, "infestedsilverfish", "betterblockoutline.blockinfo3d.infestedsilverfish", "betterblockoutline.blockinfo3d.info.infestedsilverfish");
    }

    @Override
    public boolean shouldRender(AbstractConnectedBlock block) {
        return block.getBlock().getState().getBlock() instanceof InfestedBlock;
    }

    public void refreshSilverfish() {
        silverFish = new SilverfishEntity(EntityType.SILVERFISH, client.world);
        EntityRenderDispatcher entityRenderer = client.getEntityRenderDispatcher();
        EntityRendererFactory.Context context = new EntityRendererFactory.Context(entityRenderer, client.getItemRenderer(), client.getResourceManager(), client.getEntityModelLoader(), client.textRenderer);
        renderer = new SilverfishCustomRenderer(context);
    }

    @Override
    public void renderInfo(MatrixStack matrices, Vector3d camera, Entity entity, AbstractConnectedBlock block) {
        if (silverFish == null) {
            refreshSilverfish();
        }
        // Set positions
        Vector3d pos = RenderingUtil.getCameraOffset(camera, block.getBlock().getPos());
        pos = new Vector3d(pos.x + .5, pos.y + .35, pos.z + .5);
        Vec3d vec3d = renderer.getPositionOffset(silverFish, 1);
        double x = pos.x + vec3d.getX();
        double y = pos.y + vec3d.getY();
        double z = pos.z + vec3d.getZ();

        matrices.push();
        matrices.translate(x, y, z);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(buffer);

        renderer.render(silverFish, 0, 1f, matrices, immediate, 0xF000F0);
        RenderLayer renderLayer = RenderLayer.getEntityTranslucent(renderer.getTexture(silverFish));
        buffer.setCameraPosition(0, 0, 0);
        buffer.end();
        renderLayer.startDrawing();
        // We set depth here to bypass other render layers
        RenderingUtil.setDepth(false);
        BufferRenderer.draw(buffer);
        renderLayer.endDrawing();

        RenderingUtil.setDepth(true);
        matrices.pop();
    }

    public static class SilverfishCustomRenderer extends SilverfishEntityRenderer {

        public SilverfishCustomRenderer(EntityRendererFactory.Context context) {
            super(context);
        }

        @Override
        public void render(SilverfishEntity mobEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
            // Bare bones renderer
            matrixStack.push();
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(0.0, -1.501f, 0.0);
            RenderLayer renderLayer = RenderLayer.getEntityTranslucent(getTexture(mobEntity));
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            int o = LivingEntityRenderer.getOverlay(mobEntity, this.getAnimationCounter(mobEntity, tickDelta));
            ((Model)this.model).render(matrixStack, vertexConsumer, i, o, 1.0f, 1.0f, 1.0f, .7f);
            matrixStack.pop();
        }
    }

}
