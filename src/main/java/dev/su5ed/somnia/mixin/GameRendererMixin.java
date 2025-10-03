package dev.su5ed.somnia.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.su5ed.somnia.util.ClientInjectHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "renderLevel",
            at = @At("HEAD"),
            require = 1,
            cancellable = true)
    private void doRenderLevel(float partialTicks, long finishTimeNano, PoseStack stack, CallbackInfo ci) {
        if (ClientInjectHooks.skipRenderWorld(partialTicks, finishTimeNano, stack)) {
            ci.cancel();
        }
    }
}
