package dev.su5ed.somnia.network.client;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.ClientPacketHandler;
import dev.su5ed.somnia.network.SingletonPacketPayload;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;

public final class OpenGUIPacket implements SingletonPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "open_gui");
    public static final OpenGUIPacket INSTANCE = new OpenGUIPacket();
    private OpenGUIPacket() {}

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.workHandler().execute(ClientPacketHandler::openGUI);
        }
    }
}
