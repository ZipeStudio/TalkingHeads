package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THManager;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

//? if >=1.21.11 {
import net.minecraft.client.model.player.PlayerModel;
//?} else {
/*import net.minecraft.client.model.PlayerModel;
*///?}

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}

//? if >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
//?}

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @Shadow
    public abstract EntityModel<?> getModel();

    //? if >=1.21.9 {
    @Inject(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At("HEAD")
    )
    private void onRender(LivingEntityRenderState state, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

        if (!(this.getModel() instanceof PlayerModel model)) {
            return;
        }

        if (!(state instanceof PlayerRenderStateWithParent playerState)) {
            return;
        }

        Player player = playerState.talkingheads$getEntity();
        if (player == null) return;

        UUID uuid = player.getUUID();
        THManager.renderHead(uuid, model);
    }
    //?} else if >=1.21.2 {
    /*@Inject(
            at = @At("HEAD"),
            method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
    )
    private void onRender(LivingEntityRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {

        if (!(this.getModel() instanceof PlayerModel model)) {
            return;
        }

        Player playerEntity = ((PlayerRenderStateWithParent) livingEntityRenderState).talkingheads$getEntity();
        if (playerEntity == null) return;

        UUID uuid = playerEntity.getUUID();

        THManager.renderHead(uuid, model);
    }
    *///?} else {
    /*@Inject(
            at = @At("HEAD"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
    )
    private void onRender(LivingEntity livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {

        if (!(livingEntity instanceof Player player)) {
            return;
        }

        UUID uuid = player.getUUID();

        if (!(this.getModel() instanceof PlayerModel<?> model)) {
            return;
        }

        THManager.renderHead(uuid, model);
    }
    *///?}
}