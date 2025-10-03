package dev.su5ed.somnia.mixin;

import dev.su5ed.somnia.util.InjectHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Redirect(method = "tickChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z",
                       ordinal = 0),
              require = 1)
    private boolean funnyGameRules(GameRules instance, GameRules.Key<GameRules.BooleanValue> p_46208_) {
        return InjectHooks.doMobSpawning((ServerLevel) (Object) this);
    }
}
