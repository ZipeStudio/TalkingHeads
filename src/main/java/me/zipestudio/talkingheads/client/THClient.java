package me.zipestudio.talkingheads.client;

import me.zipestudio.talkingheads.config.THConfig;
import net.fabricmc.api.ClientModInitializer;

public class THClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //? plasmovoice {
        su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new PlasmoVoiceAddon());
        //?}

        THManager.setClientConfig(THConfig.getInstance());
    }
}
