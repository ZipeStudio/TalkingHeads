package me.zipestudio.talkingheads.mixin;

//? if >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizablePlayer;
import net.minecraft.client.model.HumanoidModel;

import net.minecraft.client.model.geom.ModelPart;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.9 {
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.state.PlayerRenderState;
*///?}

//? if >=1.21.11 {
/*import net.minecraft.client.model.player.PlayerModel;
@Mixin(PlayerModel.class)
*///?} else {
import net.minecraft.client.model.PlayerModel;
@Mixin(PlayerModel.class)
//?}

public class PlayerModelMixin //? if >=1.21.9 {
 extends HumanoidModel<@NotNull AvatarRenderState>
//?} else {
 /*extends HumanoidModel<PlayerRenderState>
*///?}
{

    public PlayerModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    //? if >=1.21.9 {
    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V",
            at = @At("HEAD"))
    private void saveScaleToModel(AvatarRenderState state, CallbackInfo ci) {
        double x = ((ResizablePlayer) state).talkingHeads$getSizeX();
        double y = ((ResizablePlayer) state).talkingHeads$getSizeY();
        double z = ((ResizablePlayer) state).talkingHeads$getSizeZ();
        ((ResizablePlayer) this.head).talkingHeads$setSize(x, y, z);
    }
    //?} else if >=1.21.2 {
    /*@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)V",
            at = @At("HEAD"))
    private void saveScaleToModel(PlayerRenderState state, CallbackInfo ci) {
        double x = ((ResizablePlayer) state).talkingHeads$getSizeX();
        double y = ((ResizablePlayer) state).talkingHeads$getSizeY();
        double z = ((ResizablePlayer) state).talkingHeads$getSizeZ();
        ((ResizablePlayer) this.head).talkingHeads$setSize(x, y, z);
    }
    *///?}
}
//?}