package me.zipestudio.talkingheads.mixin;


import me.zipestudio.talkingheads.client.THAddon;
import me.zipestudio.talkingheads.client.THClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {

    @Inject(
            at = @At("HEAD"),
            method = "renderArmor"
    )

    private void renderInject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<?> model, CallbackInfo ci) {

        if (!(entity instanceof PlayerEntity || armorSlot != EquipmentSlot.HEAD)) {
            return;
        }

        UUID uuid = entity.getUuid();
        THAddon.renderHead(uuid, model);
    }
}
