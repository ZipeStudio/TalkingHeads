package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

//? if >=1.21.9 {
/*import net.minecraft.client.render.command.OrderedRenderCommandQueue;
*///?}

//? if >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
//?}

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @Shadow
    public abstract EntityModel<?> getModel();

    //? if >=1.21.9 {
    /*@Inject(
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At("HEAD")
    )
    private void onRender(LivingEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, Object cameraState, CallbackInfo ci) {

        if (!(this.getModel() instanceof PlayerEntityModel model)) {
            return;
        }

        if (!(state instanceof PlayerRenderStateWithParent playerState)) {
            return;
        }

        PlayerEntity player = playerState.talkingheads$getEntity();
        if (player == null) return;

        UUID uuid = player.getUuid();
        THManager.renderHead(uuid, model);
    }
    *///?} else if >=1.21.2 {
    @Inject(
            at = @At("HEAD"),
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
    )
    private void onRender(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (!(this.getModel() instanceof PlayerEntityModel model)) {
            return;
        }

        PlayerEntity playerEntity = ((PlayerRenderStateWithParent) livingEntityRenderState).talkingheads$getEntity();
        if (playerEntity == null) return;

        UUID uuid = playerEntity.getUuid();

        THManager.renderHead(uuid, model);
    }
    //?} else {
    /*@Inject(
            at = @At("HEAD"),
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
    )
    private void onRender(LivingEntity livingEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider consumers, int light, CallbackInfo ci) {

        if (!(livingEntity instanceof PlayerEntity player)) {
            return;
        }

        UUID uuid = player.getUuid();

        if (!(this.getModel() instanceof PlayerEntityModel<?> model)) {
            return;
        }

        THManager.renderHead(uuid, model);
    }
    *///?}
}