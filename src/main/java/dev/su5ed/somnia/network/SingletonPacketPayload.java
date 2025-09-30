package dev.su5ed.somnia.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface SingletonPacketPayload extends CustomPacketPayload {
    @Override
    default void write(@NotNull FriendlyByteBuf buffer) {

    }
}
