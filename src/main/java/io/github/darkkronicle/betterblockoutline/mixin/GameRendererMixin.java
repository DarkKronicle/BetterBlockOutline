package io.github.darkkronicle.betterblockoutline.mixin;

import io.github.darkkronicle.betterblockoutline.config.ConfigStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow private boolean blockOutlineEnabled;

    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "shouldRenderBlockOutline",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shouldRenderOutline(CallbackInfoReturnable<Boolean> ci) {
        if (!ConfigStorage.getGeneral().getActive().getValue() || !ConfigStorage.getGeneral().getAlwaysShow().getValue()) {
            return;
        }
        if (!this.blockOutlineEnabled) {
            ci.setReturnValue(false);
            return;
        }
        Entity entity = client.cameraEntity;
        if (!(entity instanceof PlayerEntity) || this.client.options.hudHidden) {
            ci.setReturnValue(false);
            return;
        }
        ci.setReturnValue(true);
    }

}
