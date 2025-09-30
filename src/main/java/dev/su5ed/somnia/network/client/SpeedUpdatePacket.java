package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.ClientSleepHandler;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import dev.su5ed.somnia.SomniaAwoken;
import io.netty.buffer.ByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SpeedUpdatePacket(double speed) implements CustomPacketPayload {
    public static final Type<SpeedUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "speed_update"));
    public static final StreamCodec<ByteBuf, SpeedUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(SpeedUpdatePacket::write, SpeedUpdatePacket::new);

    public SpeedUpdatePacket(ByteBuf buffer) {
        this(buffer.readDouble());
    }

    public void write(ByteBuf buffer) {
        buffer.writeDouble(speed);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ClientSleepHandler.INSTANCE.addSpeedValue(speed);
    }
}
