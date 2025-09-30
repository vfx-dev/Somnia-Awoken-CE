package dev.su5ed.somnia.network.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import dev.su5ed.somnia.SomniaAwoken;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ActivateBlockPacket(BlockPos pos, Direction side, float hitX, float hitY, float hitZ) implements
        CustomPacketPayload {
    public static final Type<ActivateBlockPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "activate_block"));
    public static final StreamCodec<FriendlyByteBuf, ActivateBlockPacket> STREAM_CODEC = StreamCodec.ofMember(ActivateBlockPacket::write, ActivateBlockPacket::read);

    public static ActivateBlockPacket read(FriendlyByteBuf buffer) {
        var pos = buffer.readBlockPos();
        var side = buffer.readEnum(Direction.class);
        var hitX = buffer.readFloat();
        var hitY = buffer.readFloat();
        var hitZ = buffer.readFloat();
        return new ActivateBlockPacket(pos, side, hitX, hitY, hitZ);
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeEnum(this.side);
        buffer.writeFloat(this.hitX);
        buffer.writeFloat(this.hitY);
        buffer.writeFloat(this.hitZ);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        var player = context.player();
        @SuppressWarnings("resource")
        var state = player.level().getBlockState(pos);
        var hitResult = new BlockHitResult(new Vec3(this.hitX, this.hitY, this.hitZ), this.side, pos, false);

        state.useWithoutItem(player.level(), player, hitResult);
    }
}
