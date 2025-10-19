package me.zipestudio.talkingheads.mixin.state;


//? if >=1.21.9 {
import net.minecraft.entity.PlayerLikeEntity;
//?}

//? if >=1.21.2 {

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    //? if >=1.21.9 {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
            at = @At("HEAD")
    )
    private void injectRenderState(PlayerLikeEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        if (!(player instanceof AbstractClientPlayerEntity entity)) return;
        ((PlayerRenderStateWithParent) state).talkingheads$setEntity(entity);
    }
    //?} else {
    /*@Inject(
            method = "updateRenderState*",
            at = @At("HEAD")
    )
    private void injectRenderState(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        if (player == null) return;
        ((PlayerRenderStateWithParent) state).talkingheads$setEntity(player);
    }
    *///?}

}
//?}