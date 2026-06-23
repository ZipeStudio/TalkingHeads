package me.zipestudio.talkingheads.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.client.THManager;

import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
//?}

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {

    //? if >=1.21.9 {
    @WrapOperation(
            method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
                    ordinal = 3
            )
    )
    private void onRenderArmor(HumanoidArmorLayer<?, ?, ?> instance, PoseStack matrixStack, SubmitNodeCollector queue, ItemStack stack, EquipmentSlot slot, int light, HumanoidRenderState state, Operation<Void> original, @Local(argsOnly = true) HumanoidRenderState bipedState) {

        boolean isHead = slot == EquipmentSlot.HEAD;
        Player player = (state instanceof PlayerRenderStateWithParent parent)
                ? parent.talkingheads$getEntity()
                : null;

        if (isHead && player != null) {
            if (shouldSkipHelmetRender(player)) {
                return;
            }
        }

        original.call(instance, matrixStack, queue, stack, slot, light, bipedState);

    }
    //?} elif >=1.21.2 {
    /*@WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
                    ordinal = 3
            )
    )
    private void renderInject(HumanoidArmorLayer<?, ?, ?> instance, PoseStack poseStack, MultiBufferSource multiBufferSource, ItemStack itemStack, EquipmentSlot equipmentSlot, int i, HumanoidModel<?> humanoidModel, Operation<Void> original, @Local(argsOnly = true) HumanoidRenderState bipedState) {

        boolean isHead = equipmentSlot == EquipmentSlot.HEAD;
        Player player = (bipedState instanceof PlayerRenderStateWithParent parent)
                ? parent.talkingheads$getEntity()
                : null;

        if (isHead && player != null) {
            poseStack.pushPose();
            THManager.renderHead(player.getUUID(), poseStack);

            if (shouldSkipHelmetRender(player)) {
                poseStack.popPose();
                return;
            }
        }

        original.call(instance, poseStack, multiBufferSource, itemStack, equipmentSlot, i, humanoidModel);

        if (isHead && player != null) {
            poseStack.popPose();
        }

    }
    *///?} else {

    /*//? if fabric {
    @Inject(
            at = @At("HEAD"),
            method = "renderArmorPiece*",
            cancellable = true
    )
    private void renderInject(PoseStack poseStack, MultiBufferSource multiBufferSource, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int i, HumanoidModel<?> humanoidModel, CallbackInfo ci) {

        if (!(livingEntity instanceof Player player) || equipmentSlot != EquipmentSlot.HEAD) {
            return;
        }

        UUID uuid = player.getUUID();
        THManager.renderHead(uuid, humanoidModel);

        if (shouldSkipHelmetRender(player)) {
            ci.cancel();
        }

    }
    //?} elif neoforge {
    /^@WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
                    ordinal = 3
            )
    )
    private void renderInject(
            HumanoidArmorLayer<?, ?, ?> instance,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            LivingEntity livingEntity,
            EquipmentSlot equipmentSlot,
            int packedLight,
            HumanoidModel<?> humanoidModel,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            Operation<Void> original
    ) {

        if (livingEntity instanceof Player player) {

            THManager.renderHead(player.getUUID(), humanoidModel);
            if (shouldSkipHelmetRender(player)) {
                return;
            }

        }

        original.call(instance, poseStack, multiBufferSource, livingEntity, equipmentSlot, packedLight, humanoidModel, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
    }
    ^///?} elif forge {
    /^@WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
                    ordinal = 3
            )
    )
    private void renderInject(
            HumanoidArmorLayer<?, ?, ?> instance,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            LivingEntity livingEntity,
            EquipmentSlot equipmentSlot,
            int packedLight,
            HumanoidModel<?> humanoidModel,
            Operation<Void> original
    ) {

        if (livingEntity instanceof Player player) {

            THManager.renderHead(player.getUUID(), humanoidModel);
            if (shouldSkipHelmetRender(player)) {
                return;
            }

        }

        original.call(instance, poseStack, multiBufferSource, livingEntity, equipmentSlot, packedLight, humanoidModel);
    }
    ^///?}
    *///?}

    @Unique
    private boolean shouldSkipHelmetRender(Player player) {
        if (!THClient.getLeafyConfig().isHelmetHideWhileTalking()) return false;

        Long lastTalk = THManager.LAST_TALK_TIME.get(player.getUUID());
        boolean playerTalking = THManager.isPlayerTalking(player.getUUID());

        long now = System.currentTimeMillis();
        long delayMs = (long) (THClient.getLeafyConfig().getHelmetShowDelay() * 1000);
        boolean inDelay = lastTalk != null && (now - lastTalk) < delayMs;

        return playerTalking || inDelay;
    }

}
