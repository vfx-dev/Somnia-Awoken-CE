package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public final class PlayerWakeUpPacket implements CustomPacketPayload {
    public static final Type<PlayerWakeUpPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "player_wake_up"));
    public static final PlayerWakeUpPacket INSTANCE = new PlayerWakeUpPacket();
    public static final StreamCodec<Object, PlayerWakeUpPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    private PlayerWakeUpPacket() {}

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ClientPacketHandler.wakeUpPlayer();
    }
}
