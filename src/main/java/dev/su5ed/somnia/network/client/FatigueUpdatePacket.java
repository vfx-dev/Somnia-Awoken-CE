package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record FatigueUpdatePacket(double fatigue) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "fatigue_update");

    public FatigueUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readDouble());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeDouble(fatigue);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.workHandler().execute(() -> ClientPacketHandler.updateFatigue(fatigue));
        }
    }
}
