package dev.su5ed.somnia.network.server;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.capability.Fatigue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.netty.buffer.ByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ResetSpawnPacket(boolean resetSpawn) implements CustomPacketPayload {
    public static final Type<ResetSpawnPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "reset_spawn"));
    public static final StreamCodec<ByteBuf, ResetSpawnPacket> STREAM_CODEC = StreamCodec.ofMember(ResetSpawnPacket::write, ResetSpawnPacket::new);

    public ResetSpawnPacket(ByteBuf buffer) {
        this(buffer.readBoolean());
    }

    public void write(ByteBuf buffer) {
        buffer.writeBoolean(resetSpawn);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        var player = context.player();

        Fatigue fatigue = player.getCapability(CapabilityFatigue.INSTANCE);
        if (fatigue != null) {
            fatigue.setResetSpawn(this.resetSpawn);
        }
    }
}
