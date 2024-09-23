package me.zipestudio.talkingheads.client;

import lombok.Getter;
import lombok.Setter;
import me.zipestudio.talkingheads.config.THConfig;
import me.zipestudio.talkingheads.utils.ResizableModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.UUID;


@Getter @Setter
public class THManager {

    @Getter @Setter
    private static THConfig clientConfig;

    @Getter
    public static final HashMap<UUID, THPlayer> PLAYERS = new HashMap<>();


    public static void renderHead(UUID uuid, BipedEntityModel<?> model) {
        HashMap<UUID, THPlayer> playersMap = getPLAYERS();
        THPlayer thPlayerInfo = playersMap.get(uuid);

        if (thPlayerInfo != null) {
            double playerVolume = thPlayerInfo.getPlayerVolume();
            double size = 1 + playerVolume;

            if (playerVolume <= 0.01) {
                playersMap.remove(uuid);
                ((ResizableModelPart) model.head).talkingHeads$setSize(size);
                ((ResizableModelPart) model.hat).talkingHeads$setSize(size);
                return;
            }

            ((ResizableModelPart) model.head).talkingHeads$setSize(size);
            ((ResizableModelPart) model.hat).talkingHeads$setSize(size);
            thPlayerInfo.setPlayerVolume(playerVolume - 0.0020);
        } else {
            playersMap.remove(uuid);
            ((ResizableModelPart) model.head).talkingHeads$setDefaultSize();
            ((ResizableModelPart) model.hat).talkingHeads$setDefaultSize();
        }
    }

    public static void renderHead(UUID uuid, MatrixStack matrixStack) {
        HashMap<UUID, THPlayer> playersMap = getPLAYERS();
        THPlayer thPlayerInfo = playersMap.get(uuid);

        if (thPlayerInfo != null) {
            double playerVolume = thPlayerInfo.getPlayerVolume();
            double size = 1 + playerVolume;

            if (playerVolume <= 0.01) {
                playersMap.remove(uuid);
                matrixStack.scale((float) 1, (float) 1, (float) 1);
                return;
            }

            matrixStack.scale((float) size, (float) size, (float) size);
            thPlayerInfo.setPlayerVolume(playerVolume - 0.0020);
        } else {
            matrixStack.scale((float) 1, (float) 1, (float) 1);
        }
    }

}
