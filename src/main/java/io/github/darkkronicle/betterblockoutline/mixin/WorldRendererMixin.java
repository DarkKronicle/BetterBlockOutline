package io.github.darkkronicle.betterblockoutline.mixin;

import io.github.darkkronicle.betterblockoutline.BlockOutlineManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(
            method = "drawBlockOutline",
            at = @At("HEAD"),
            cancellable = true
    )
    private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos pos, BlockState state, CallbackInfo ci) {
        BlockOutlineManager.getInstance().drawOutline(matrices, vertexConsumer, entity, d, e, f, pos, state);
        ci.cancel();
    }

}
