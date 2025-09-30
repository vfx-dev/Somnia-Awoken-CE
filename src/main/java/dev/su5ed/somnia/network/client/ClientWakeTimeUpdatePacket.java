package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ClientWakeTimeUpdatePacket(long wakeTime) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "client_wake_time_update");

    public ClientWakeTimeUpdatePacket(final FriendlyByteBuf buffer) {
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
        if (context.flow().isClientbound()) {
            context.workHandler().execute(() -> ClientPacketHandler.updateWakeTime(wakeTime));
        }
    }
}
