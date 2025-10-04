package dev.su5ed.somnia.network.server;

import dev.su5ed.somnia.capability.CapabilityFatigue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class ResetSpawnPacket {
    private final boolean resetSpawn;

    public ResetSpawnPacket(boolean resetSpawn) {
        this.resetSpawn = resetSpawn;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.resetSpawn);
    }

    public static ResetSpawnPacket decode(FriendlyByteBuf buf) {
        boolean resetSpawn = buf.readBoolean();
        return new ResetSpawnPacket(resetSpawn);
    }

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.getSender().getCapability(CapabilityFatigue.INSTANCE)
            .ifPresent(props -> props.setResetSpawn(this.resetSpawn));
    }
}
