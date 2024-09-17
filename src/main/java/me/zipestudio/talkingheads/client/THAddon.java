package me.zipestudio.talkingheads.client;

import lombok.Getter;
import me.zipestudio.talkingheads.utils.ResizableModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
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
        id = "pv-addon-talkingheads",
        name = "Talking Heads",
        version = "1.0.0",
        authors = {"ZipeStudio"}
)
public class THAddon implements AddonInitializer {

    @InjectPlasmoVoice
    @Getter
    public static PlasmoVoiceClient voiceClient;
    private ClientActivation activation;

    public static final HashMap<UUID, THPlayerInfo> PLAYERS_MAP = new HashMap<>();
    private double lastClientAudioLevel;

    @Override
    public void onAddonInitialize() {

        System.out.println("Addon initialized");
    }

    @EventSubscribe
    public void onSourceWrite(@NotNull AudioSourceWriteEvent event) {
        if (!THClient.getClientConfig().isToggle()) {
            return;
        }

        var sourceInfo = event.getSource().getSourceInfo();
        if (!(sourceInfo instanceof PlayerSourceInfo playerSourceInfo)) return;

        VoicePlayerInfo playerInfo = playerSourceInfo.getPlayerInfo();
        UUID uuid = playerInfo.getPlayerId();
        double audioLevel = AudioUtil.calculateHighestAudioLevel(event.getSamples());

        System.out.println("audio: " + audioLevel);

        if (audioLevel <= -60D) {
            PLAYERS_MAP.put(uuid, new THPlayerInfo(playerInfo, 0));
            return;
        }

        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));
        PLAYERS_MAP.put(uuid, new THPlayerInfo(playerInfo, voiceVolume));
    }

    @EventSubscribe
    public void onSelfAudioPacket(@NotNull UdpClientPacketReceivedEvent event) {
        if (!THClient.getClientConfig().isToggle()) {
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
        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));

        if (audioLevel <= -60D) {
            PLAYERS_MAP.remove(uuid);
            return;
        }

        PLAYERS_MAP.put(uuid, new THPlayerInfo(playerInfo, voiceVolume));
    }

    @EventSubscribe
    public void onAudioCapture(@NotNull AudioCaptureProcessedEvent event) {
        if (!THClient.getClientConfig().isToggle()) {
            return;
        }

        this.lastClientAudioLevel = AudioUtil.calculateHighestAudioLevel(event.getProcessed().getMono());
    }

    public static void renderHead(UUID uuid, BipedEntityModel<?> model) {
        THPlayerInfo thPlayerInfo = PLAYERS_MAP.get(uuid);

        if (thPlayerInfo != null) {

            double playerVolume = thPlayerInfo.getPlayerVolume();
            double size = 1 + playerVolume;

            if (playerVolume <= 0.01) {
                PLAYERS_MAP.remove(uuid);
                ((ResizableModelPart) model.head).talkingHeads$setSize(size);
                ((ResizableModelPart) model.hat).talkingHeads$setSize(size);
                return;
            }

            ((ResizableModelPart) model.head).talkingHeads$setSize(size);
            ((ResizableModelPart) model.hat).talkingHeads$setSize(size);
            thPlayerInfo.setPlayerVolume(playerVolume - 0.0020);
        } else {
            PLAYERS_MAP.remove(uuid);
            ((ResizableModelPart) model.head).talkingHeads$setDefaultSize();
            ((ResizableModelPart) model.hat).talkingHeads$setDefaultSize();
        }
    }

    public static void renderHead(UUID uuid, MatrixStack matrixStack) {
        THPlayerInfo thPlayerInfo = PLAYERS_MAP.get(uuid);

        if (thPlayerInfo != null) {

            double playerVolume = thPlayerInfo.getPlayerVolume();
            double size = 1 + playerVolume;

            if (playerVolume <= 0.01) {
                PLAYERS_MAP.remove(uuid);
                matrixStack.scale((float) 1, (float) 1, (float) 1);
                return;
            }

            matrixStack.scale((float) size, (float) size, (float) size);
            thPlayerInfo.setPlayerVolume(playerVolume - 0.0020);
        } else {
            matrixStack.scale((float) 1, (float) 1, (float) 1);
        }
    }

    @Override
    public void onAddonShutdown() {

        System.out.println("Addon shut down");
    }
}
