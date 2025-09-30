package dev.su5ed.somnia.network.server;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.capability.Fatigue;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record WakeTimeUpdatePacket(long wakeTime) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "wake_time_update");

    public WakeTimeUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readLong());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(wakeTime);
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
            fatigue.setWakeTime(this.wakeTime);
        }
    }
}
