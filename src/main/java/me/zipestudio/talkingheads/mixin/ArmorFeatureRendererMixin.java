package me.zipestudio.talkingheads.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.zipestudio.talkingheads.client.THManager;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
//?}

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {

    //? >=1.21.2 {

    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
                    ordinal = 3
            )
    )
    private void renderInject(ArmorFeatureRenderer<?, ?, ?> instance, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, ItemStack itemStack, EquipmentSlot equipmentSlot, int i, BipedEntityModel<BipedEntityRenderState> bipedEntityModel, Operation<Void> original, @Local(argsOnly = true) BipedEntityRenderState bipedEntityRenderState) {

        if (!(bipedEntityRenderState instanceof PlayerRenderStateWithParent playerState))
            return;

        PlayerEntity playerEntity = playerState.talkingheads$getEntity();
        if (playerEntity == null) return;

        if (equipmentSlot != EquipmentSlot.HEAD) return;

        matrixStack.push();
        THManager.renderHead(playerEntity.getUuid(), matrixStack);
        matrixStack.pop();
    }

    //?} else {

    /*@Inject(
            at = @At("HEAD"),
            method = "renderArmor"
    )

    private void renderInject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<?> model, CallbackInfo ci) {

        if (!(entity instanceof PlayerEntity || armorSlot != EquipmentSlot.HEAD)) {
            return;
        }

        UUID uuid = entity.getUuid();
        THManager.renderHead(uuid, model);
    }

    *///?}

}
