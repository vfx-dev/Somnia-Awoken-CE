package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import dev.su5ed.somnia.network.SingletonPacketPayload;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;

public final class PlayerWakeUpPacket implements SingletonPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "player_wake_up");
    public static final PlayerWakeUpPacket INSTANCE = new PlayerWakeUpPacket();
    private PlayerWakeUpPacket() {}


    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.workHandler().execute(ClientPacketHandler::wakeUpPlayer);
        }
    }
}
