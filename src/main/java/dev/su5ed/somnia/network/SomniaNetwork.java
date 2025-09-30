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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.function.Consumer;

public final class SomniaNetwork {
    public static void registerMessages(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar(SomniaAwoken.MODID);
        int id = 0;


        // Client messages
        registrar.playToClient(ClientWakeTimeUpdatePacket.TYPE, ClientWakeTimeUpdatePacket.STREAM_CODEC, ClientWakeTimeUpdatePacket::handle);
        registrar.playToClient(FatigueUpdatePacket.TYPE, FatigueUpdatePacket.STREAM_CODEC, FatigueUpdatePacket::handle);
        registrar.playToClient(OpenGUIPacket.TYPE, OpenGUIPacket.STREAM_CODEC, OpenGUIPacket::handle);
        registrar.playToClient(PlayerWakeUpPacket.TYPE, PlayerWakeUpPacket.STREAM_CODEC, PlayerWakeUpPacket::handle);
        registrar.playToClient(SpeedUpdatePacket.TYPE, SpeedUpdatePacket.STREAM_CODEC, SpeedUpdatePacket::handle);

        // Server messages
        registrar.playToServer(ActivateBlockPacket.TYPE, ActivateBlockPacket.STREAM_CODEC, ActivateBlockPacket::handle);
        registrar.playToServer(ResetSpawnPacket.TYPE, ResetSpawnPacket.STREAM_CODEC, ResetSpawnPacket::handle);
        registrar.playToServer(WakeTimeUpdatePacket.TYPE, WakeTimeUpdatePacket.STREAM_CODEC, WakeTimeUpdatePacket::handle);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static void sendToClient(CustomPacketPayload packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void sendToDimension(CustomPacketPayload packet, ServerLevel dimension) {
        PacketDistributor.sendToPlayersInDimension(dimension, packet);
    }

    private SomniaNetwork() {}
}
