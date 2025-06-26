package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THManager;
import me.zipestudio.talkingheads.utils.interfaces.ResizableModelPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

//? >=1.21.2 {
/*import me.zipestudio.talkingheads.utils.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
*///?}

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @Shadow
    public abstract EntityModel<?> getModel();

    //? >=1.21.2 {


    /*@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (!(this.getModel() instanceof PlayerEntityModel model)) {
            return;
        }

        PlayerEntity playerEntity = ((PlayerRenderStateWithParent) livingEntityRenderState).talkingheads$getEntity();
        if (playerEntity == null) {
            return;
        }

        UUID uuid = playerEntity.getUuid();

        THManager.renderHead(uuid, model);
    }

    *///?} else {

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render(LivingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }

        UUID uuid = livingEntity.getUuid();

        if (!(this.getModel() instanceof PlayerEntityModel<?> model)) {
            return;
        }

        THManager.renderHead(uuid, model);
    }

	//?}
}
