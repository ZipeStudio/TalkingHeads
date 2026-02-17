package me.zipestudio.talkingheads.client;

import lombok.Getter;
import lombok.Setter;
import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.utils.talkingheads.interfaces.ResizablePlayer;
import me.zipestudio.talkingheads.utils.talkingheads.THPlayerProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//? if >=1.21.9 {
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?} else if >=1.21.2 {
/*import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
*///?}

@Getter
@Setter
public class THManager {

    public static final Map<UUID, THPlayerProfile> PLAYERS_MAP = new ConcurrentHashMap<>();

    public static final HashMap<UUID, Long> LAST_TALK_TIME = new HashMap<>();

    private static double MIN_VOICE_VALUE = 0.01;

    private static final LeafyConfig CONFIG = LeafyConfig.getInstance();

    public static boolean isPlayerTalking(UUID uuid) {
        THPlayerProfile profile = PLAYERS_MAP.get(uuid);
        return profile != null && profile.getPlayerVolume() > MIN_VOICE_VALUE;
    }

    public static void decrementAll() {
        Iterator<Map.Entry<UUID, THPlayerProfile>> iterator = PLAYERS_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, THPlayerProfile> entry = iterator.next();
            THPlayerProfile profile = entry.getValue();
            double newVolume = profile.getPlayerVolume() - CONFIG.getRemovedVolume();
            if (newVolume <= MIN_VOICE_VALUE) {
                iterator.remove(); // удаляем замолчавших
            } else {
                profile.setPlayerVolume(newVolume);
            }
        }
    }

    //? if >=1.21.2 {
    //? if >=1.21.9 {
    public static void renderHead(UUID uuid, AvatarRenderState renderState)
    //?} else {
    /*public static void renderHead(UUID uuid, PlayerRenderState renderState)
     *///?}
    {
        if (isPlayerTalking(uuid)) {
            LAST_TALK_TIME.put(uuid, System.currentTimeMillis());
        }

        THPlayerProfile profile = PLAYERS_MAP.get(uuid);
        if (profile != null && profile.getPlayerVolume() > MIN_VOICE_VALUE) {
            double sizeX = 1 + CONFIG.getScaleX() * profile.getPlayerVolume();
            double sizeY = 1 + CONFIG.getScaleY() * profile.getPlayerVolume();
            double sizeZ = 1 + CONFIG.getScaleZ() * profile.getPlayerVolume();
            ((ResizablePlayer) renderState).talkingHeads$setSize(sizeX, sizeY, sizeZ);
        } else {
            ((ResizablePlayer) renderState).talkingHeads$setDefaultsSize();
        }
    }
    //?} else {
    /*public static void renderHead(UUID uuid, HumanoidModel<?> model) {

        if (isPlayerTalking(uuid)) {
            LAST_TALK_TIME.put(uuid, System.currentTimeMillis());
        }

        THPlayerProfile profile = PLAYERS_MAP.get(uuid);
        if (profile != null && profile.getPlayerVolume() > MIN_VOICE_VALUE) {
            double sizeX = 1 + CONFIG.getScaleX() * profile.getPlayerVolume();
            double sizeY = 1 + CONFIG.getScaleY() * profile.getPlayerVolume();
            double sizeZ = 1 + CONFIG.getScaleZ() * profile.getPlayerVolume();
            ((ResizablePlayer) model.head).talkingHeads$setSize(sizeX, sizeY, sizeZ);
            ((ResizablePlayer) model.hat).talkingHeads$setSize(sizeX, sizeY, sizeZ);
        } else {
            ((ResizablePlayer) model.head).talkingHeads$setDefaultsSize();
            ((ResizablePlayer) model.hat).talkingHeads$setDefaultsSize();
        }
    }
    *///?}

    public static void renderHead(UUID uuid, PoseStack matrixStack) {

        if (isPlayerTalking(uuid)) {
            LAST_TALK_TIME.put(uuid, System.currentTimeMillis());
        }

        THPlayerProfile profile = PLAYERS_MAP.get(uuid);
        if (profile != null && profile.getPlayerVolume() > MIN_VOICE_VALUE) {
            double sizeX = 1 + CONFIG.getScaleX() * profile.getPlayerVolume();
            double sizeY = 1 + CONFIG.getScaleY() * profile.getPlayerVolume();
            double sizeZ = 1 + CONFIG.getScaleZ() * profile.getPlayerVolume();
            matrixStack.scale((float) sizeX, (float) sizeY, (float) sizeZ);
        } else {
            matrixStack.scale(1, 1, 1);
        }
    }

}
