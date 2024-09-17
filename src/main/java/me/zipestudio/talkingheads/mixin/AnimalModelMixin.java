package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THClient;
import me.zipestudio.talkingheads.utils.ResizableModelPart;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalModel.class)
public abstract class AnimalModelMixin {

    @Inject(at = @At(value = "HEAD"), method = "method_22949")
    private static void renderBeforeHead(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int k, ModelPart modelPart, CallbackInfo ci) {
        if (modelPart instanceof ResizableModelPart p && p.talkingHeads$getSize() != p.talkingHeads$getDefaultSize()) {
            if (THClient.getClientConfig().isToggle()) {
                matrixStack.push();
                matrixStack.scale((float) p.talkingHeads$getSize(), (float) p.talkingHeads$getSize(), (float) p.talkingHeads$getSize());
            }
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "method_22949")
    private static void renderAfterHead(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int k, ModelPart modelPart, CallbackInfo ci) {
        if (modelPart instanceof ResizableModelPart p && p.talkingHeads$getSize() != p.talkingHeads$getDefaultSize()) {

            if (THClient.getClientConfig().isToggle()) {
                matrixStack.pop();
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "method_22947")
    private static void renderBeforeHat(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int k, ModelPart modelPart, CallbackInfo ci) {
        if (modelPart instanceof ResizableModelPart p && p.talkingHeads$getSize() != p.talkingHeads$getDefaultSize()) {

            if (THClient.getClientConfig().isToggle()) {
                matrixStack.push();
                matrixStack.scale((float) p.talkingHeads$getSize(), (float) p.talkingHeads$getSize(), (float) p.talkingHeads$getSize());
            }
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "method_22947")
    private static void renderAfterHat(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int k, ModelPart modelPart, CallbackInfo ci) {
        if (modelPart instanceof ResizableModelPart p && p.talkingHeads$getSize() != p.talkingHeads$getDefaultSize()) {
            if (THClient.getClientConfig().isToggle()) {
                matrixStack.pop();
            }
        }
    }

}
