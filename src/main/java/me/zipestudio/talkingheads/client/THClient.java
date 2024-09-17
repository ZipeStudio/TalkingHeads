package me.zipestudio.talkingheads.client;

import lombok.Getter;
import me.zipestudio.talkingheads.config.THConfig;
import net.fabricmc.api.ClientModInitializer;
import su.plo.voice.api.client.PlasmoVoiceClient;

public class THClient implements ClientModInitializer {

    private final THAddon addon = new THAddon();
    @Getter
    private static THConfig clientConfig;

    @Override
    public void onInitializeClient() {
        PlasmoVoiceClient.getAddonsLoader().load(addon);

        clientConfig = THConfig.getInstance();
    }

    public static void setConfig(THConfig thConfig) {
        clientConfig = thConfig;
    }
}
