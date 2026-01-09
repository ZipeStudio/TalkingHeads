package me.zipestudio.talkingheads.utils.talkingheads.interfaces;

import net.minecraft.world.entity.player.Player;

@SuppressWarnings("java:S100")
public interface PlayerRenderStateWithParent {
    void talkingheads$setEntity(Player entity);
    Player talkingheads$getEntity();
}