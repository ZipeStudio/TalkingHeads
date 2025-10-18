package me.zipestudio.talkingheads.client;

import lombok.Getter;
import lombok.Setter;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizableModelPart;
import me.zipestudio.talkingheads.utils.talkingheads.THPlayerProfile;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.UUID;


@Getter
@Setter
public class THManager {
    public static final HashMap<UUID, THPlayerProfile> PLAYERS_MAP = new HashMap<>();

    public static void renderHead(UUID uuid, BipedEntityModel<?> model) {
        THPlayerProfile thPlayerProfileInfo = PLAYERS_MAP.get(uuid);

        if (thPlayerProfileInfo != null) {

            double playerVolume = thPlayerProfileInfo.getPlayerVolume();

            LeafyConfig leafyConfig = THClient.getLeafyConfig();

            double sizeX = 1 + leafyConfig.getScaleX() * playerVolume;
            double sizeY = 1 + leafyConfig.getScaleY() * playerVolume;
            double sizeZ = 1 + leafyConfig.getScaleZ() * playerVolume;

            if (playerVolume <= 0.01) {
                PLAYERS_MAP.remove(uuid);
                ((ResizableModelPart) model.head).talkingHeads$setDefaultSize();

                //? <1.21.2 {
                /*((ResizableModelPart) model.hat).talkingHeads$setDefaultSize();
                 *///?}
                return;
            }

            ((ResizableModelPart) model.head).talkingHeads$setSize(sizeX, sizeY, sizeZ);

            //? <1.21.2 {
            /*((ResizableModelPart) model.hat).talkingHeads$setSize(sizeX, sizeY, sizeZ);
             *///?}

            thPlayerProfileInfo.setPlayerVolume(playerVolume - leafyConfig.getRemovedVolume());
        } else {
            PLAYERS_MAP.remove(uuid);
            ((ResizableModelPart) model.head).talkingHeads$setDefaultSize();

            //? <1.21.2 {
            /*((ResizableModelPart) model.hat).talkingHeads$setDefaultSize();
             *///?}
        }
    }

    public static void renderHead(UUID uuid, MatrixStack matrixStack) {
        THPlayerProfile thPlayerProfileInfo = PLAYERS_MAP.get(uuid);

        if (thPlayerProfileInfo != null) {
            double playerVolume = thPlayerProfileInfo.getPlayerVolume();

            LeafyConfig leafyConfig = THClient.getLeafyConfig();

            double sizeX = 1 + leafyConfig.getScaleX() * playerVolume;
            double sizeY = 1 + leafyConfig.getScaleY() * playerVolume;
            double sizeZ = 1 + leafyConfig.getScaleZ() * playerVolume;

            if (playerVolume <= 0.01) {
                PLAYERS_MAP.remove(uuid);

                matrixStack.scale(1, 1, 1);
                return;
            }

            matrixStack.scale((float) sizeX, (float) sizeY, (float) sizeZ);

            thPlayerProfileInfo.setPlayerVolume(playerVolume - leafyConfig.getRemovedVolume());
        } else {
            PLAYERS_MAP.remove(uuid);
            matrixStack.scale(1, 1, 1);
        }
    }

}
