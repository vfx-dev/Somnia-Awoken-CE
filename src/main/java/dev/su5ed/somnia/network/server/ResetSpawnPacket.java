package dev.su5ed.somnia.network.server;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.capability.Fatigue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ResetSpawnPacket(boolean resetSpawn) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "reset_spawn");

    public ResetSpawnPacket(FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(resetSpawn);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        if (!context.flow().isServerbound())
            return;
        var playerOpt = context.player();
        if (playerOpt.isEmpty()) {
            return;
        }
        var player = playerOpt.get();

        Fatigue fatigue = player.getCapability(CapabilityFatigue.INSTANCE);
        if (fatigue != null) {
            fatigue.setResetSpawn(this.resetSpawn);
        }
    }
}
