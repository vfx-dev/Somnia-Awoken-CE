package dev.su5ed.somnia.network;

import dev.su5ed.somnia.SomniaAwoken;
import dev.su5ed.somnia.network.client.ClientWakeTimeUpdatePacket;
import dev.su5ed.somnia.network.client.FatigueUpdatePacket;
import dev.su5ed.somnia.network.client.OpenGUIPacket;
import dev.su5ed.somnia.network.client.PlayerWakeUpPacket;
import dev.su5ed.somnia.network.client.SpeedUpdatePacket;
import dev.su5ed.somnia.network.server.ActivateBlockPacket;
import dev.su5ed.somnia.network.server.ResetSpawnPacket;
import dev.su5ed.somnia.network.server.WakeTimeUpdatePacket;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;

import java.util.function.Consumer;

public final class SomniaNetwork {
    public static void registerMessages(final RegisterPayloadHandlerEvent event) {
        final var registrar = event.registrar(SomniaAwoken.MODID);
        int id = 0;


        // Client messages
        registrar.play(ClientWakeTimeUpdatePacket.ID, ClientWakeTimeUpdatePacket::new, ClientWakeTimeUpdatePacket::handle);
        registrar.play(FatigueUpdatePacket.ID, FatigueUpdatePacket::new, FatigueUpdatePacket::handle);
        registrar.play(OpenGUIPacket.ID, buffer -> OpenGUIPacket.INSTANCE, OpenGUIPacket::handle);
        registrar.play(PlayerWakeUpPacket.ID, buffer -> PlayerWakeUpPacket.INSTANCE, PlayerWakeUpPacket::handle);
        registrar.play(SpeedUpdatePacket.ID, SpeedUpdatePacket::new, SpeedUpdatePacket::handle);

        // Server messages
        registrar.play(ActivateBlockPacket.ID, ActivateBlockPacket::read, ActivateBlockPacket::handle);
        registrar.play(ResetSpawnPacket.ID, ResetSpawnPacket::new, ResetSpawnPacket::handle);
        registrar.play(WakeTimeUpdatePacket.ID, WakeTimeUpdatePacket::new, WakeTimeUpdatePacket::handle);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.SERVER.noArg().send(packet);
    }

    public static void sendToClient(CustomPacketPayload packet, ServerPlayer player) {
        PacketDistributor.PLAYER.with(player).send(packet);
    }

    public static void sendToDimension(CustomPacketPayload packet, ResourceKey<Level> dimension) {
        PacketDistributor.DIMENSION.with(dimension).send(packet);
    }

    private SomniaNetwork() {}
}
