package me.zipestudio.talkingheads.mixin.state;

//? if >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

//? if >=1.21.9 {
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
@Mixin(AvatarRenderState.class)
//?} else {
/*import net.minecraft.client.renderer.entity.state.PlayerRenderState;
@Mixin(PlayerRenderState.class)
*///?}
public class AvatarRenderStateMixin implements PlayerRenderStateWithParent {

    @Unique
    private Player talkingheads$entity;

    @Override
    public void talkingheads$setEntity(Player entity) {
        if (entity == null) return;
        this.talkingheads$entity = entity;
    }

    @Override
    public Player talkingheads$getEntity() {
        return this.talkingheads$entity;
    }

}
//?}


