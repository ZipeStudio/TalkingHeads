package me.zipestudio.talkingheads.mixin;

import lombok.Getter;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizableModelPart;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements ResizableModelPart {

    @Unique
    private double sizeX = 1;

    @Unique
    private double sizeY = 1;

    @Unique
    private double sizeZ = 1;

    @Override
    public void talkingHeads$setSize(double sizeX, double sizeY, double sizeZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    @Override
    public void talkingHeads$setDefaultsSize() {
        this.sizeX = talkingHeads$getDefaultSize();
        this.sizeY = talkingHeads$getDefaultSize();
        this.sizeZ = talkingHeads$getDefaultSize();
    }

    @Override
    public boolean talkingHeads$isDefaults() {
        double def = talkingHeads$getDefaultSize();
        return this.talkingHeads$getSizeX() == def && this.talkingHeads$getSizeY() == def && this.talkingHeads$getSizeZ() == def;
    }

    @Override
    public double talkingHeads$getSizeX() {
        return this.sizeX;
    }

    @Override
    public double talkingHeads$getSizeY() {
        return this.sizeY;
    }

    @Override
    public double talkingHeads$getSizeZ() {
        return this.sizeZ;
    }

    //? if <1.21.5 {
    @Inject(method = "rotate(Lnet/minecraft/client/util/math/MatrixStack;)V",
            at = @At(value = "HEAD")
    )
    //?} else {
    /*@Inject(method = "applyTransform",
            at = @At(value = "HEAD")
    )
    *///?}
    public void scaleHeadNew(MatrixStack matrices, CallbackInfo ci) {

        if (talkingHeads$isDefaults()) return;

        matrices.scale(
                (float) talkingHeads$getSizeX(),
                (float) talkingHeads$getSizeY(),
                (float) talkingHeads$getSizeZ()
        );

    }

}
