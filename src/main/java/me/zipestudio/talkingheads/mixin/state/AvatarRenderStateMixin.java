package me.zipestudio.talkingheads.mixin.state;

//? if >=1.21.2 {
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.PlayerRenderStateWithParent;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizablePlayer;
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
public class AvatarRenderStateMixin implements PlayerRenderStateWithParent, ResizablePlayer {

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
    public void talkingHeads$setDefaultsSize() {
        this.sizeX = talkingHeads$getDefaultSize();
        this.sizeY = talkingHeads$getDefaultSize();
        this.sizeZ = talkingHeads$getDefaultSize();
    }

    @Override
    public boolean talkingHeads$isDefaults() {
        double def = talkingHeads$getDefaultSize();
        return this.talkingHeads$getSizeX() == def && this.talkingHeads$getSizeY() == def && this.talkingHeads$getSizeZ() == def;
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