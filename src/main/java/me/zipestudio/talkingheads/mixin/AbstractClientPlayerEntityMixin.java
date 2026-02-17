package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

//? if >=1.21.9 {
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.ClientAsset.*;
//?} elif >1.20.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.resources.PlayerSkin;
*///?} else {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Shadow @Nullable private PlayerInfo playerInfo;

    @Unique
    private static final Identifier TH_CAPE_ID = Identifier.fromNamespaceAndPath(THServer.MOD_ID, "textures/cape/talking_heads_cape.png");

    //? if >=1.21.9 {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerInfo;getSkin()Lnet/minecraft/world/entity/player/PlayerSkin;"), method = "getSkin")
    private PlayerSkin customCape(PlayerInfo instance, Operation<PlayerSkin> original) {
        PlayerSkin call = original.call(instance);
        ClientAsset.Texture capeTexture = call.cape();
        if (capeTexture != null || !THClient.AUTHORS.contains(instance.getProfile().id())) {
            return call;
        }
        return new PlayerSkin(call.body(), new ResourceTexture(TH_CAPE_ID, TH_CAPE_ID), call.elytra(), call.model(), call.secure());
    }
    //?} elif >1.20.1 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerInfo;getSkin()Lnet/minecraft/client/resources/PlayerSkin;"), method = "getSkin")
	private PlayerSkin customCape(PlayerInfo instance, Operation<PlayerSkin> original) {
		PlayerSkin call = original.call(instance);
		Identifier capeTexture = call.capeTexture();
		if (capeTexture != null || !THClient.AUTHORS.contains(instance.getProfile().getId())) {
			return call;
		}
		return new PlayerSkin(call.texture(), call.textureUrl(), TH_CAPE_ID, call.elytraTexture(), call.model(), call.secure());
	}
	*///?} else {
	/*@Shadow @Nullable protected abstract PlayerInfo getPlayerInfo();

	@Inject(method = "getCloakTextureLocation", at = @At("RETURN"), cancellable = true)
	private void customCape(CallbackInfoReturnable<Identifier> cir) {
		Identifier original = cir.getReturnValue();
		if (original != null) {
			return;
		}
		PlayerInfo playerListEntry = this.getPlayerInfo();
		if (playerListEntry == null) {
			return;
		}
		if (THClient.AUTHORS.contains(playerListEntry.getProfile().getId())) {
			cir.setReturnValue(TH_CAPE_ID);
		}
	}
	*///?}
}