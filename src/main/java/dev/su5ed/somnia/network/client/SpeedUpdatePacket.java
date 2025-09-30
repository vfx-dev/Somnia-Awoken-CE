package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.ClientSleepHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import dev.su5ed.somnia.SomniaAwoken;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record SpeedUpdatePacket(double speed) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "speed_update");

    public SpeedUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readDouble());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeDouble(speed);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.workHandler().execute(() -> ClientSleepHandler.INSTANCE.addSpeedValue(speed));
        }
    }
}
