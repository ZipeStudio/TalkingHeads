package me.zipestudio.talkingheads.client;

import lombok.Getter;
import lombok.Setter;
import me.zipestudio.talkingheads.config.THConfig;
import me.zipestudio.talkingheads.utils.interfaces.ResizableModelPart;
import me.zipestudio.talkingheads.utils.THVolumePlayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.UUID;


@Getter
@Setter
public class THManager {
    public static final HashMap<UUID, THVolumePlayer> PLAYERS_MAP = new HashMap<>();

    public static void renderHead(UUID uuid, BipedEntityModel<?> model) {
        THVolumePlayer thVolumePlayerInfo = PLAYERS_MAP.get(uuid);

        if (thVolumePlayerInfo != null) {

            double playerVolume = thVolumePlayerInfo.getPlayerVolume();

            THConfig thConfig = THClient.getConfig();

            double sizeX = 1 + thConfig.getScaleX() * playerVolume;
            double sizeY = 1 + thConfig.getScaleY() * playerVolume;
            double sizeZ = 1 + thConfig.getScaleZ() * playerVolume;

            if (playerVolume <= 0.01) {
                PLAYERS_MAP.remove(uuid);
                ((ResizableModelPart) model.head).talkingHeads$setDefaultSize();

                //? <1.21.2 {
                ((ResizableModelPart) model.hat).talkingHeads$setDefaultSize();
                 //?}
                return;
            }

            ((ResizableModelPart) model.head).talkingHeads$setSize(sizeX, sizeY, sizeZ);

            //? <1.21.2 {
            ((ResizableModelPart) model.hat).talkingHeads$setSize(sizeX, sizeY, sizeZ);
             //?}

            thVolumePlayerInfo.setPlayerVolume(playerVolume - thConfig.getRemovedVolume());
        } else {
            PLAYERS_MAP.remove(uuid);
            ((ResizableModelPart) model.head).talkingHeads$setDefaultSize();

            //? <1.21.2 {
            ((ResizableModelPart) model.hat).talkingHeads$setDefaultSize();
             //?}
        }
    }

    public static void renderHead(UUID uuid, MatrixStack matrixStack) {
        THVolumePlayer thVolumePlayerInfo = PLAYERS_MAP.get(uuid);

        if (thVolumePlayerInfo != null) {
            double playerVolume = thVolumePlayerInfo.getPlayerVolume();

            THConfig thConfig = THClient.getConfig();

            double sizeX = 1 + thConfig.getScaleX() * playerVolume;
            double sizeY = 1 + thConfig.getScaleY() * playerVolume;
            double sizeZ = 1 + thConfig.getScaleZ() * playerVolume;

            if (playerVolume <= 0.01) {
                PLAYERS_MAP.remove(uuid);

                matrixStack.scale(1, 1, 1);
                return;
            }

            matrixStack.scale((float) sizeX, (float) sizeY, (float) sizeZ);

            thVolumePlayerInfo.setPlayerVolume(playerVolume - thConfig.getRemovedVolume());
        } else {
            PLAYERS_MAP.remove(uuid);
            matrixStack.scale(1, 1, 1);
        }
    }

}
