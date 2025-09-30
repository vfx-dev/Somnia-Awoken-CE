package dev.su5ed.somnia.mixin;

import dev.su5ed.somnia.util.ClientInjectHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "renderLevel",
            at = @At("HEAD"),
            require = 1,
            cancellable = true)
    private void doRenderLevel(DeltaTracker tracker, CallbackInfo ci) {
        if (ClientInjectHooks.skipRenderWorld()) {
            ci.cancel();
        }
    }
}
