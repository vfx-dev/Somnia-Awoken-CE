package dev.su5ed.somnia.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.su5ed.somnia.SomniaConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.server.level.ServerPlayer;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @WrapOperation(method = "startSleepInBed",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z"),
                   require = 1)
    private boolean ignoreMonsters(ServerPlayer instance, Operation<Boolean> original) {
        return SomniaConfig.COMMON.ignoreMonsters.get() || original.call(instance);
    }
}
