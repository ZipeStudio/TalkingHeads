package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THManager;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
 //?}

//? if >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
//?}

@Mixin(CustomHeadLayer.class)
public class CustomHeadLayerMixin {

    //? if >=1.21.9 {
    @Inject(
            method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void onRender(PoseStack matrices, SubmitNodeCollector queue, int light, LivingEntityRenderState state, float f, float g, CallbackInfo ci) {

        if (!(state instanceof PlayerRenderStateWithParent playerState)) {
            return;
        }

        Player player = playerState.talkingheads$getEntity();
        if (player == null) return;

        THManager.renderHead(player.getUUID(), matrices);
    }
    //?} else if >=1.21.2 {
    /*@Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void renderInject(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntityRenderState livingEntityRenderState, float f, float g, CallbackInfo ci) {

        if (!(livingEntityRenderState instanceof PlayerRenderStateWithParent playerRenderStateWithParent)) {
            return;
        }

        Player playerEntity = playerRenderStateWithParent.talkingheads$getEntity();
        if (playerEntity == null) return;

        THManager.renderHead(playerEntity.getUUID(), poseStack);
    }
    *///?} else {
    /*@Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void renderInject(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {

        if (!(livingEntity instanceof Player)) {
            return;
        }

        UUID uuid = livingEntity.getUUID();
        THManager.renderHead(uuid, poseStack);
    }
    *///?}
}
