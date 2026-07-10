package me.zipestudio.talkingheads.mixin;

//? if >=1.21.1 {
import net.minecraft.client.DeltaTracker;
//?} else {
/*import net.minecraft.client.Timer;
*///?}

import me.zipestudio.talkingheads.client.THManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftTickMixin {

    //? if >=1.21.4 {

    @Shadow
    @Final
    private DeltaTracker.Timer deltaTracker;

    @Inject(
            method = "runTick",
            at = @At("HEAD")
    )
    private void onRunTick(CallbackInfo ci) {
        THManager.decrementAll(this.deltaTracker.getGameTimeDeltaPartialTick(true));
    }

    //?} elif >=1.21.1 {

    /*@Shadow
    public abstract DeltaTracker getTimer();

    @Inject(
            method = "runTick",
            at = @At("HEAD")
    )
    private void onRunTick(CallbackInfo ci) {
        THManager.decrementAll(this.getTimer().getGameTimeDeltaPartialTick(true));
    }

    *///?} else {

    /*@Shadow
    @Final
    private Timer timer;

    @Inject(
            method = "runTick",
            at = @At("HEAD")
    )
    private void onRunTick(CallbackInfo ci) {
        THManager.decrementAll(this.timer.tickDelta);
    }

    *///?}

}
