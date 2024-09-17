package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THAddon;
import me.zipestudio.talkingheads.client.THClient;
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

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {


    @Shadow
    public abstract EntityModel<?> getModel();

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render(LivingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }
        UUID uuid = livingEntity.getUuid();

        if (!(this.getModel() instanceof PlayerEntityModel<?> model)) {
            return;
        }

        THAddon.renderHead(uuid, model);
    }

}
