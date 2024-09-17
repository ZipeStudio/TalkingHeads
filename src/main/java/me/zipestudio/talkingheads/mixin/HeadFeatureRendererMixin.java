package me.zipestudio.talkingheads.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.zipestudio.talkingheads.client.THAddon;
import me.zipestudio.talkingheads.client.THClient;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(HeadFeatureRenderer.class)
public class HeadFeatureRendererMixin {

    @WrapOperation(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V"
    )
    private void renderInject(MatrixStack instance, float x, float y, float z, Operation<Void> original, @Local(argsOnly = true) LivingEntity livingEntity) {

        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }

            UUID uuid = livingEntity.getUuid();
            THAddon.renderHead(uuid, instance);
    }

}
