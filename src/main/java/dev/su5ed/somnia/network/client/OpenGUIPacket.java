package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public final class OpenGUIPacket implements CustomPacketPayload {
    public static final Type<OpenGUIPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "open_gui"));
    public static final OpenGUIPacket INSTANCE = new OpenGUIPacket();
    public static final StreamCodec<Object, OpenGUIPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private OpenGUIPacket() {}

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ClientPacketHandler.openGUI();
    }
}
