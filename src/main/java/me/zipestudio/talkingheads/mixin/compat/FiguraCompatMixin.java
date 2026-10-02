package me.zipestudio.talkingheads.mixin.compat;

import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizablePlayer;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.figuramc.figura.model.VanillaModelData$PartData", remap = false)
public class FiguraCompatMixin {

	@Inject(method = "updateFromPart", at = @At("HEAD"), require = 0)
	private void talkingHeads$applyScale(ModelPart part, CallbackInfo ci) {
		if (part instanceof ResizablePlayer resizable) {
			part.xScale = (float) resizable.talkingHeads$getSizeX();
			part.yScale = (float) resizable.talkingHeads$getSizeY();
			part.zScale = (float) resizable.talkingHeads$getSizeZ();
		}
	}

	@Inject(method = "updateFromPart", at = @At("TAIL"), require = 0)
	private void talkingHeads$resetScale(ModelPart part, CallbackInfo ci) {
		if (part instanceof ResizablePlayer resizable) {
			float defaultSize = (float) resizable.talkingHeads$getDefaultSize();
			part.xScale = defaultSize;
			part.yScale = defaultSize;
			part.zScale = defaultSize;
		}
	}

}
