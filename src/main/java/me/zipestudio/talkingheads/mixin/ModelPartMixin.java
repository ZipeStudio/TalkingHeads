package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THManager;
import me.zipestudio.talkingheads.utils.interfaces.ResizableModelPart;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

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
    public void talkingHeads$setDefaultSize() {
        this.sizeX = 1;
        this.sizeY = 1;
        this.sizeZ = 1;
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

    @Inject(method = "rotate(Lnet/minecraft/client/util/math/MatrixStack;)V",
            at = @At(value = "HEAD")
    )
    public void scaleHead(MatrixStack matrices, CallbackInfo ci) {

        double x = this.talkingHeads$getSizeX();
        double y = this.talkingHeads$getSizeY();
        double z = this.talkingHeads$getSizeZ();

        boolean xScale = x != this.talkingHeads$getDefaultSize();
        boolean yScale = y != this.talkingHeads$getDefaultSize();
        boolean zScale = z != this.talkingHeads$getDefaultSize();

        if (xScale || yScale || zScale) {
            matrices.scale((float) x, (float) y, (float) z);
        }

    }

}
