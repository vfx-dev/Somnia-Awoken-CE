package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.netty.buffer.ByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record FatigueUpdatePacket(double fatigue) implements CustomPacketPayload {
    public static final Type<FatigueUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "fatigue_update"));
    public static final StreamCodec<ByteBuf, FatigueUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(FatigueUpdatePacket::write, FatigueUpdatePacket::new);

    public FatigueUpdatePacket(ByteBuf buffer) {
        this(buffer.readDouble());
    }

    public void write(ByteBuf buffer) {
        buffer.writeDouble(fatigue);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ClientPacketHandler.updateFatigue(fatigue);
    }
}
