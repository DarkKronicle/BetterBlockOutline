package io.github.darkkronicle.betterblockoutline.mixin;

import io.github.darkkronicle.betterblockoutline.BlockOutlineManager;
import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;
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
        if (ConfigStorage.getGeneral().getActive().getValue()) {
            BlockOutlineManager.getInstance().drawOutline(matrices, vertexConsumer, entity, d, e, f, pos, state);
            ci.cancel();
        }
    }

    @Inject(
            method = "render",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", shift = At.Shift.BEFORE)
    )
    public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        BlockOutlineManager.getInstance().render(matrices, camera);
    }
}
