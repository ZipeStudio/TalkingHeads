package me.zipestudio.talkingheads.mixin.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizablePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
//? if >=26.1 {
@Mixin(targets = "com.tom.cpm.client.PlayerRenderManager$CPMModelPart", remap = false)
//?} else {
/*@Mixin(targets = "com.tom.cpm.client.PlayerRenderManager$RedirectModelRendererVanilla", remap = false)
 *///?}
public class CPMCompatMixin {

    //? if >=1.21 {
    @Inject(method = {"render", "method_22699"}, at = @At("HEAD"), require = 0)
    private void talkingHeads$applyHeadScale(PoseStack stack, VertexConsumer buffer, int light, int overlay, int color, CallbackInfo ci) {
        //?} else {
	/*@Inject(method = {"render", "method_22699"}, at = @At("HEAD"), require = 0)
	private void talkingHeads$applyHeadScale(PoseStack stack, VertexConsumer buffer, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
	*///?}

        if (!(this instanceof ResizablePlayer resizable) || resizable.talkingHeads$isDefaults()) {
            return;
        }

        stack.pushPose();

        stack.scale(
                (float) resizable.talkingHeads$getSizeX(),
                (float) resizable.talkingHeads$getSizeY(),
                (float) resizable.talkingHeads$getSizeZ()
        );
    }

    //? if >=1.21 {
    @Inject(method = {"render", "method_22699"}, at = @At("RETURN"), require = 0)
    private void talkingHeads$restorePose(PoseStack stack, VertexConsumer buffer, int light, int overlay, int color, CallbackInfo ci) {
        //?} else {
	/*@Inject(method = {"render", "method_22699"}, at = @At("RETURN"), require = 0)
	private void talkingHeads$restorePose(PoseStack stack, VertexConsumer buffer, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
	*///?}

        if (!(this instanceof ResizablePlayer resizable) || resizable.talkingHeads$isDefaults()) {
            return;
        }

        stack.popPose();
    }

}
