package me.zipestudio.talkingheads.mixin;

import me.zipestudio.talkingheads.client.THManager;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(
            method = "renderLevel",
            at = @At("HEAD")
    )
    private void onRenderLevel(CallbackInfo ci) {
        THManager.decrementAll();
    }

}