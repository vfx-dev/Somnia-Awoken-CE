package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class FatigueUpdatePacket {
    private final double fatigue;

    public FatigueUpdatePacket(double fatigue) {
        this.fatigue = fatigue;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.fatigue);
    }

    public static FatigueUpdatePacket decode(FriendlyByteBuf buf) {
        double fatigue = buf.readDouble();
        return new FatigueUpdatePacket(fatigue);
    }

    public void handle(CustomPayloadEvent.Context ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.updateFatigue(this.fatigue));
    }
}
