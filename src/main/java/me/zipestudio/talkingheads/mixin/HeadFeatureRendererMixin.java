package me.zipestudio.talkingheads.mixin;

import de.maxhenkel.voicechat.api.Player;
import me.zipestudio.talkingheads.client.THManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
//?}

@Mixin(HeadFeatureRenderer.class)
public class HeadFeatureRendererMixin {

    //? >=1.21.2 {
    @Inject(
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V"
    )
    private void renderInject(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntityRenderState livingEntityRenderState, float f, float g, CallbackInfo ci) {

        if (!(livingEntityRenderState instanceof PlayerRenderStateWithParent playerRenderStateWithParent)) {
            return;
        }

        PlayerEntity playerEntity = playerRenderStateWithParent.talkingheads$getEntity();
        if (playerEntity == null) return;

        THManager.renderHead(playerEntity.getUuid(), matrixStack);
    }
    //?} else {
         /*@Inject(
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V   ",
                    ordinal = 0,
                    shift = At.Shift.AFTER),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V"
    )
    private void renderInject(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {

        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }

        UUID uuid = livingEntity.getUuid();

        THManager.renderHead(uuid, matrixStack);

    }
    *///?}
}
