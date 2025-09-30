package dev.su5ed.somnia.network.server;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.capability.Fatigue;
import io.netty.buffer.ByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record WakeTimeUpdatePacket(long wakeTime) implements CustomPacketPayload {
    public static final Type<WakeTimeUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "wake_time_update"));
    public static final StreamCodec<ByteBuf, WakeTimeUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(WakeTimeUpdatePacket::write, WakeTimeUpdatePacket::new);

    public WakeTimeUpdatePacket(ByteBuf buffer) {
        this(buffer.readLong());
    }

    public void write(ByteBuf buffer) {
        buffer.writeLong(wakeTime);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        var player = context.player();

        Fatigue fatigue = player.getCapability(CapabilityFatigue.INSTANCE);
        if (fatigue != null) {
            fatigue.setWakeTime(this.wakeTime);
        }
    }
}
