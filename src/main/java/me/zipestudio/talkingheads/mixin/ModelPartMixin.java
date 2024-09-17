package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.utils.ResizableModelPart;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.*;

@Mixin(ModelPart.class)
public class ModelPartMixin implements ResizableModelPart {

	@Unique
	private double size = 1;

	@Override
	public void talkingHeads$setSize(double size) {
		this.size = size;
	}

	@Override
	public double talkingHeads$getSize() {
		return this.size;
	}

	@Override
	public double talkingHeads$getDefaultSize() {
		return 1;
	}

	@Override
	public void talkingHeads$setDefaultSize() {
		this.size = 1;
	}
}
