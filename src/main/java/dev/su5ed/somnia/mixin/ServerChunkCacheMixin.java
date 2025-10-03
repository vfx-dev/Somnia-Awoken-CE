package dev.su5ed.somnia.mixin;

import dev.su5ed.somnia.util.InjectHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {
    @Shadow @Final public ServerLevel level;

    @Redirect(method = "tickChunks",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"),
              require = 1)
    private boolean customMobSpawning(GameRules instance, GameRules.Key<GameRules.BooleanValue> p_46208_) {
        return InjectHooks.doMobSpawning(level);
    }
}
