package me.zipestudio.talkingheads.utils;

import me.zipestudio.talkingheads.client.THManager;

import java.util.HashMap;
import java.util.UUID;

public class AudioUtils {

    private static final HashMap<UUID, THVolumePlayer> PLAYERS_MAP = THManager.PLAYERS_MAP;

    public static double calculateAudioLevel(short[] samples) {
        if (samples == null || samples.length == 0) {
            return -127D;
        }

        double sum = 0D;

        // Вычисляем RMS
        for (short sample : samples) {
            double normalized = sample / (double) Short.MAX_VALUE;
            sum += normalized * normalized;
        }

        double rms = Math.sqrt(sum / samples.length);

        double db = (rms > 0D) ? Math.max(20D * Math.log10(rms), -127D) : -127D;
        return db;
    }

    public static void applyHeadVolume(UUID playerUuid, double audioLevel) {
        if (audioLevel <= -50D) {
            PLAYERS_MAP.put(playerUuid, new THVolumePlayer(playerUuid, 0));
            return;
        }

        double voiceVolume = 0.8D * (1D - (Math.abs(audioLevel) / 60D));
        PLAYERS_MAP.put(playerUuid, new THVolumePlayer(playerUuid, (float) voiceVolume));
    }
}
