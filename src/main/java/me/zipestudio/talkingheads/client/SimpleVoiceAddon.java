package me.zipestudio.talkingheads.client;

//? voicechat {
/*import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import lombok.val;
import me.zipestudio.talkingheads.TalkingHeads;
import me.zipestudio.talkingheads.modmenu.AudioUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class SimpleVoiceAddon implements VoicechatPlugin {

    VoicechatApi voicechatApi;

    HashMap<UUID, THPlayer> PLAYERS_MAP = THManager.getPLAYERS();

    @Override
    public String getPluginId() {
        return "vc-addon-" + TalkingHeads.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        VoicechatPlugin.super.initialize(api);

        voicechatApi = api;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        VoicechatPlugin.super.registerEvents(registration);

        registration.registerEvent(ClientReceiveSoundEvent.class, this::on123);
        registration.registerEvent(EntitySoundPacketEvent.class, this::onEntitySoundPacketEvent);
        registration.registerEvent(MicrophonePacketEvent.class, this::onClientReceiveSoundEvent);
    }

    public void on123(ClientReceiveSoundEvent event) {

        UUID id = event.getId();
        System.out.println("ClientReceiveSoundEvent: " + id);
    }

    public void onEntitySoundPacketEvent(EntitySoundPacketEvent event) {
        UUID uuid = event.getSenderConnection().getPlayer().getUuid();

        System.out.println("EntitySoundPacketEvent: " + uuid);
    }

    public void onClientReceiveSoundEvent(MicrophonePacketEvent event) {

        System.out.println("MicrophonePacketEvent");

        UUID uuid = event.getSenderConnection().getPlayer().getUuid();
        if (uuid == null) {
            System.out.println("return");
            return;
        }

        byte[] opusEncodedData = event.getPacket().getOpusEncodedData();
        short[] decode = voicechatApi.createDecoder().decode(opusEncodedData);

        double audioLevel = AudioUtils.calculateAudioLevel(decode);
        if (audioLevel <= -50D) {
            PLAYERS_MAP.remove(uuid);
            return;
        }

        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));
        PLAYERS_MAP.put(uuid, new THPlayer(uuid, voiceVolume));
    }
}
*///?}
