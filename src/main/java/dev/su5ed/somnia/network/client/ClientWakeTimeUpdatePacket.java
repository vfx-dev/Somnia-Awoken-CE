package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.netty.buffer.ByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ClientWakeTimeUpdatePacket(long wakeTime) implements CustomPacketPayload {
    public static final Type<ClientWakeTimeUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "client_wake_time_update"));
    public static final StreamCodec<ByteBuf, ClientWakeTimeUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(ClientWakeTimeUpdatePacket::write, ClientWakeTimeUpdatePacket::new);
    public ClientWakeTimeUpdatePacket(final ByteBuf buffer) {
        this(buffer.readLong());
    }

    public void write(ByteBuf buffer) {
        buffer.writeLong(wakeTime);
    }

    @Override
    public @NotNull Type<ClientWakeTimeUpdatePacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ClientPacketHandler.updateWakeTime(wakeTime);
    }
}
