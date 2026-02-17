package me.zipestudio.talkingheads.utils.talkingheads;

import me.zipestudio.talkingheads.client.THManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AudioUtils {

    private static final Map<UUID, THPlayerProfile> ACTIVE_PLAYERS_MAP = THManager.PLAYERS_MAP;

    public static double calculateAudioLevel(short[] samples) {

        if (samples == null || samples.length == 0) {
            return -127D;
        }

        double sum = 0D;
        for (short sample : samples) {
            double normalized = sample / (double) Short.MAX_VALUE;
            sum += normalized * normalized;
        }

        double rms = Math.sqrt(sum / samples.length);
        return (rms > 0D) ? Math.max(20D * Math.log10(rms), -127D) : -127D;
    }

    public static void applyHeadVolume(UUID playerUuid, double audioLevel) {

        double TALKING_THRESHOLD = -40D;
        if (audioLevel <= TALKING_THRESHOLD) {
            ACTIVE_PLAYERS_MAP.remove(playerUuid);
            return;
        }

        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));
        ACTIVE_PLAYERS_MAP.put(playerUuid, new THPlayerProfile(playerUuid, (float) voiceVolume));
    }

}