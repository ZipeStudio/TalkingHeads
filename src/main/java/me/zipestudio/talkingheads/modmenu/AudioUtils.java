package me.zipestudio.talkingheads.modmenu;

public class AudioUtils {
    public static double calculateAudioLevel(short[] samples) {
        double rms = 0D; // root mean square (RMS) amplitude

        for (short value : samples) {
            double sample = (double) value / (double) Short.MAX_VALUE;
            rms += sample * sample;
        }

        int sampleCount = samples.length / 2;

        rms = (sampleCount == 0) ? 0 : Math.sqrt(rms / sampleCount);

        double db;

        if (rms > 0D) {
            db = Math.min(Math.max(20D * Math.log10(rms), -127D), 0D);
        } else {
            db = -127D;
        }

        return db;
    }
}
