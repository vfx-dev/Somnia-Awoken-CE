package dev.su5ed.somnia.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.su5ed.somnia.SomniaConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Redirect(method = "lambda$startSleepInBed$13",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/level/Level;isDay()Z"),
              require = 1)
    private boolean ignoreDay(Level instance) {
        return false;
    }
    @WrapOperation(method = "lambda$startSleepInBed$13",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z"),
                   require = 1)
    private boolean ignoreMonsters(ServerPlayer instance, Operation<Boolean> original) {
        return SomniaConfig.COMMON.ignoreMonsters.get() || original.call(instance);
    }
}
