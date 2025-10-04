package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class PlayerWakeUpPacket {
    public void handle(CustomPayloadEvent.Context ctx) {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientPacketHandler::wakeUpPlayer);
    }
}
