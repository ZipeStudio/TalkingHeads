package me.zipestudio.talkingheads.client;

import lombok.Getter;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.config.THConfig;
import me.zipestudio.talkingheads.utils.AudioUtils;
import org.jetbrains.annotations.NotNull;
import su.plo.voice.api.addon.AddonInitializer;
import su.plo.voice.api.addon.InjectPlasmoVoice;
import su.plo.voice.api.addon.annotation.Addon;
import su.plo.voice.api.client.PlasmoVoiceClient;
import su.plo.voice.api.client.event.audio.capture.AudioCaptureProcessedEvent;
import su.plo.voice.api.client.event.audio.source.AudioSourceWriteEvent;
import su.plo.voice.api.client.event.connection.UdpClientPacketReceivedEvent;
import su.plo.voice.api.event.EventSubscribe;
import su.plo.voice.proto.data.audio.source.PlayerSourceInfo;
import su.plo.voice.proto.data.player.VoicePlayerInfo;
import su.plo.voice.proto.packets.udp.clientbound.SelfAudioInfoPacket;

import java.util.*;

@Addon(
        id = "pv-addon-" + THServer.MOD_ID,
        name = THServer.MOD_NAME,
        version = THServer.MOD_VERSION,
        authors = {THServer.MOD_AUTHORS}
)
public class PlasmoVoiceAddon implements AddonInitializer {

    @InjectPlasmoVoice
    @Getter
    private static PlasmoVoiceClient voiceClient;

    private double lastClientAudioLevel;

    @Override
    public void onAddonInitialize() {

        System.out.println("pv-addon-" + THServer.MOD_ID + " initialized");
    }

    @EventSubscribe
    public void onSourceWrite(@NotNull AudioSourceWriteEvent event) {
        if (THConfig.isModDisabled() || !THConfig.usePlasmoVoice()) {
            return;
        }

        var sourceInfo = event.getSource().getSourceInfo();
        if (!(sourceInfo instanceof PlayerSourceInfo playerSourceInfo)) return;

        VoicePlayerInfo playerInfo = playerSourceInfo.getPlayerInfo();
        UUID uuid = playerInfo.getPlayerId();

        double audioLevel = AudioUtils.calculateAudioLevel(event.getSamples());
        AudioUtils.applyHeadVolume(uuid, audioLevel);
    }

    @EventSubscribe
    public void onSelfAudioPacket(@NotNull UdpClientPacketReceivedEvent event) {
        if (THConfig.isModDisabled() || !THConfig.usePlasmoVoice()) {
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
        AudioUtils.applyHeadVolume(uuid, audioLevel);
    }

    @EventSubscribe
    public void onAudioCapture(@NotNull AudioCaptureProcessedEvent event) {
        if (THConfig.isModDisabled() || !THConfig.usePlasmoVoice()) {
            return;
        }

//        this.lastClientAudioLevel = AudioUtil.calculateHighestAudioLevel(event.getProcessed().getMono());
        this.lastClientAudioLevel = AudioUtils.calculateAudioLevel(event.getProcessed().getMono());
    }

    @Override
    public void onAddonShutdown() {

        System.out.println("Addon shut down");
    }


}