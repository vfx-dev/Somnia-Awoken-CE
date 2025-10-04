package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.network.ClientPacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

import java.util.function.Supplier;

public class OpenGUIPacket {
    public void handle(CustomPayloadEvent.Context ctx) {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientPacketHandler::openGUI);
    }
}
