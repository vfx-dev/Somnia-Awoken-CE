package dev.su5ed.somnia.network.server;

import com.google.common.base.MoreObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import dev.su5ed.somnia.SomniaAwoken;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ActivateBlockPacket(BlockPos pos, Direction side, float hitX, float hitY, float hitZ) implements
        CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SomniaAwoken.MODID, "activate_block");

    public static ActivateBlockPacket read(FriendlyByteBuf buffer) {
        var pos = buffer.readBlockPos();
        var side = buffer.readEnum(Direction.class);
        var hitX = buffer.readFloat();
        var hitY = buffer.readFloat();
        var hitZ = buffer.readFloat();
        return new ActivateBlockPacket(pos, side, hitX, hitY, hitZ);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeEnum(this.side);
        buffer.writeFloat(this.hitX);
        buffer.writeFloat(this.hitY);
        buffer.writeFloat(this.hitZ);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        if (!context.flow().isServerbound())
            return;
        var playerOpt = context.player();
        if (playerOpt.isEmpty()) {
            return;
        }
        var player = playerOpt.get();
        @SuppressWarnings("resource")
        var state = player.level().getBlockState(pos);
        var hitResult = new BlockHitResult(new Vec3(this.hitX, this.hitY, this.hitZ), this.side, pos, false);

        state.use(player.level(), player, MoreObjects.firstNonNull(player.swingingArm, InteractionHand.MAIN_HAND), hitResult);
    }
}
