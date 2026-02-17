package me.zipestudio.talkingheads.mixin.state;

//? if >=1.21.2 {

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.player.AbstractClientPlayer;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;

//? if >=1.21.9 {
import net.minecraft.world.entity.Avatar;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
@Mixin(AvatarRenderer.class)
//?} else {
/*import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
@Mixin(PlayerRenderer.class)
*///?}
public class AvatarRendererMixin {

    //? if >=1.21.9 {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
            at = @At("HEAD")
    )
    private void injectRenderState(Avatar player, AvatarRenderState state, float tickDelta, CallbackInfo ci) {
        if (!(player instanceof AbstractClientPlayer entity)) return;
        ((PlayerRenderStateWithParent) state).talkingheads$setEntity(entity);
    }
    //?} else {
    /*@Inject(
            method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V",
            at = @At("HEAD")
    )
    private void injectRenderState(AbstractClientPlayer player, PlayerRenderState state, float f, CallbackInfo ci) {
        if (player == null) return;
        ((PlayerRenderStateWithParent) state).talkingheads$setEntity(player);
    }
    *///?}

}
//?}