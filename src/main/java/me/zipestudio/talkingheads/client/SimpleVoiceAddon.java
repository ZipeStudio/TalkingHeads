package me.zipestudio.talkingheads.client;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.utils.talkingheads.AudioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.util.UUID;

@Slf4j
public class SimpleVoiceAddon implements VoicechatPlugin {

    @Getter
    private static VoicechatApi voicechatApi;

    @Override
    public String getPluginId() {
        return "vc-addon-" + THServer.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        voicechatApi = api;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(ClientReceiveSoundEvent.EntitySound.class, this::onAnotherPlayerSoundEvent);
        registration.registerEvent(ClientSoundEvent.class, this::onClientPlayerSoundEvent);
    }

    public void onAnotherPlayerSoundEvent(ClientReceiveSoundEvent.EntitySound event) {
        LeafyConfig leafyConfig = THClient.getLeafyConfig();
        if (!leafyConfig.isEnableMod() || !leafyConfig.isUseSimpleVoiceChat()) {
            return;
        }

        UUID sourceUuid = event.getId();
        double audioLevel = AudioUtils.calculateAudioLevel(event.getRawAudio());

        AudioUtils.applyHeadVolume(sourceUuid, audioLevel);
    }

    public void onClientPlayerSoundEvent(ClientSoundEvent event) {
        LeafyConfig leafyConfig = THClient.getLeafyConfig();
        if (!leafyConfig.isEnableMod() || !leafyConfig.isUseSimpleVoiceChat()) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        UUID sourceUuid = player.getUUID();
        double audioLevel = AudioUtils.calculateAudioLevel(event.getRawAudio());

        AudioUtils.applyHeadVolume(sourceUuid, audioLevel);
    }

}
