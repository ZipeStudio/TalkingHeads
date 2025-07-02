package me.zipestudio.talkingheads.mixin.state;

//? >=1.21.2 {

import me.zipestudio.talkingheads.utils.interfaces.PlayerRenderStateWithParent;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerRenderStateWithParent {

    @Unique
    private PlayerEntity talkingheads$entity;

    @Override
    public void talkingheads$setEntity(PlayerEntity entity) {
        this.talkingheads$entity = entity;
    }

    @Override
    public PlayerEntity talkingheads$getEntity() {
        return this.talkingheads$entity;
    }

}
//?}


