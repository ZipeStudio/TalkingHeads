package me.zipestudio.talkingheads.client;

//? plasmovoice {
import lombok.Getter;
import me.zipestudio.talkingheads.TalkingHeads;
import org.jetbrains.annotations.NotNull;
import su.plo.voice.api.addon.AddonInitializer;
import su.plo.voice.api.addon.InjectPlasmoVoice;
import su.plo.voice.api.addon.annotation.Addon;
import su.plo.voice.api.client.PlasmoVoiceClient;
import su.plo.voice.api.client.audio.capture.ClientActivation;
import su.plo.voice.api.client.event.audio.capture.AudioCaptureProcessedEvent;
import su.plo.voice.api.client.event.audio.source.AudioSourceWriteEvent;
import su.plo.voice.api.client.event.connection.UdpClientPacketReceivedEvent;
import su.plo.voice.api.event.EventSubscribe;
import su.plo.voice.api.util.AudioUtil;
import su.plo.voice.proto.data.audio.source.PlayerSourceInfo;
import su.plo.voice.proto.data.player.VoicePlayerInfo;
import su.plo.voice.proto.packets.udp.clientbound.SelfAudioInfoPacket;

import java.util.*;

@Addon(
        id = "pv-addon-" + TalkingHeads.MOD_ID,
        name = TalkingHeads.MOD_NAME,
        version = TalkingHeads.MOD_VERSION,
        authors = {TalkingHeads.MOD_AUTHORS}
)
public class PlasmoVoiceAddon implements AddonInitializer {

    @InjectPlasmoVoice
    @Getter
    public static PlasmoVoiceClient voiceClient;
    private ClientActivation activation;

    HashMap<UUID, THPlayer> PLAYERS_MAP = THManager.getPLAYERS();
    private double lastClientAudioLevel;

    @Override
    public void onAddonInitialize() {

        System.out.println("Addon initialized");
    }

    @EventSubscribe
    public void onSourceWrite(@NotNull AudioSourceWriteEvent event) {
        if (isModDisabled()) {
            return;
        }

        var sourceInfo = event.getSource().getSourceInfo();
        if (!(sourceInfo instanceof PlayerSourceInfo playerSourceInfo)) return;

        VoicePlayerInfo playerInfo = playerSourceInfo.getPlayerInfo();
        UUID uuid = playerInfo.getPlayerId();

        double audioLevel = AudioUtil.calculateHighestAudioLevel(event.getSamples());
        if (audioLevel <= -50D) {
            PLAYERS_MAP.put(uuid, new THPlayer(uuid, 0));
            return;
        }

        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));
        PLAYERS_MAP.put(uuid, new THPlayer(uuid, voiceVolume));
    }

    @EventSubscribe
    public void onSelfAudioPacket(@NotNull UdpClientPacketReceivedEvent event) {
        if (isModDisabled()) {
            return;
        }

        var packet = event.getPacket();
        if (!(packet instanceof SelfAudioInfoPacket infoPacket)) {
            return;
        }

        var selfSourceInfo = voiceClient.getSourceManager().getSelfSourceInfo(infoPacket.getSourceId());
        if (selfSourceInfo.isEmpty()) {
            return;
        }

        if (!(selfSourceInfo.get().getSelfSourceInfo().getSourceInfo() instanceof PlayerSourceInfo playerSourceInfo)) {
            return;
        }

        VoicePlayerInfo playerInfo = playerSourceInfo.getPlayerInfo();
        UUID uuid = playerInfo.getPlayerId();

        double audioLevel = lastClientAudioLevel;
        if (audioLevel <= -50D) {
            PLAYERS_MAP.remove(uuid);
            return;
        }

        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));
        PLAYERS_MAP.put(uuid, new THPlayer(uuid, voiceVolume));
    }

    @EventSubscribe
    public void onAudioCapture(@NotNull AudioCaptureProcessedEvent event) {
        if (isModDisabled()) {
            return;
        }

        this.lastClientAudioLevel = AudioUtil.calculateHighestAudioLevel(event.getProcessed().getMono());
    }

    @Override
    public void onAddonShutdown() {

        System.out.println("Addon shut down");
    }

    private boolean isModDisabled() {
        return !THManager.getClientConfig().isToggle();
    }
}
//?}
