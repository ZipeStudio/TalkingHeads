package me.zipestudio.talkingheads.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.zipestudio.talkingheads.client.THClient;
import me.zipestudio.talkingheads.client.THManager;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

//? if >=1.21.9 {
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
 //?}

//? >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
//?}

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {


    //? if >=1.21.9 {
    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;)V",
                    ordinal = 3
            )
    )
    private void onRenderArmor(ArmorFeatureRenderer<?, ?, ?> instance, MatrixStack matrixStack, OrderedRenderCommandQueue queue, ItemStack stack, EquipmentSlot slot, int light, BipedEntityRenderState state, Operation<Void> original, @Local(argsOnly = true) BipedEntityRenderState bipedState) {

        boolean isHead = slot == EquipmentSlot.HEAD;
        PlayerEntity player = (state instanceof PlayerRenderStateWithParent parent)
                ? parent.talkingheads$getEntity()
                : null;

        if (isHead && player != null) {
            matrixStack.push();
            THManager.renderHead(player.getUuid(), matrixStack);

            if (shouldSkipHelmetRender(player)) {
                matrixStack.pop();
                return;
            }
        }

        original.call(instance, matrixStack, queue, stack, slot, light, bipedState);

        if (isHead && player != null) {
            matrixStack.pop();
        }

    }
    //?} else if >=1.21.2 {
    /*@WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
                    ordinal = 3
            )
    )
    private void renderInject(ArmorFeatureRenderer<?, ?, ?> instance, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, ItemStack itemStack, EquipmentSlot equipmentSlot, int i, BipedEntityModel<BipedEntityRenderState> bipedEntityModel, Operation<Void> original, @Local(argsOnly = true) BipedEntityRenderState renderState) {

        boolean isHead = equipmentSlot == EquipmentSlot.HEAD;
        PlayerEntity player = (renderState instanceof PlayerRenderStateWithParent parent)
                ? parent.talkingheads$getEntity()
                : null;

        if (isHead && player != null) {
            matrixStack.push();
            THManager.renderHead(player.getUuid(), matrixStack);

            if (shouldSkipHelmetRender(player)) {
                matrixStack.pop();
                return;
            }
        }

        original.call(instance, matrixStack, vertexConsumerProvider, itemStack, equipmentSlot, i, bipedEntityModel);

        if (isHead && player != null) {
            matrixStack.pop();
        }

    }

    *///?} else {

    /*@Inject(
            at = @At("HEAD"),
            method = "renderArmor",
            cancellable = true
    )
    private void renderInject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<?> model, CallbackInfo ci) {

        if (!(entity instanceof PlayerEntity player) || armorSlot != EquipmentSlot.HEAD) {
            return;
        }

        UUID uuid = player.getUuid();
        THManager.renderHead(uuid, model);

        if (shouldSkipHelmetRender(player)) {
            ci.cancel();
        }

    }
    *///?}

    @Unique
    private boolean shouldSkipHelmetRender(PlayerEntity player) {
        if (!THClient.getLeafyConfig().isHelmetHideWhileTalking()) return false;

        Long lastTalk = THManager.LAST_TALK_TIME.get(player.getUuid());
        boolean playerTalking = THManager.isPlayerTalking(player.getUuid());

        long now = System.currentTimeMillis();
        long delayMs = (long) (THClient.getLeafyConfig().getHelmetShowDelay() * 1000);
        boolean inDelay = lastTalk != null && (now - lastTalk) < delayMs;

        return playerTalking || inDelay;
    }

}
