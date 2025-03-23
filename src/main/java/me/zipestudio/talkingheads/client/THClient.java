package me.zipestudio.talkingheads.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class THClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        FabricLoader instance = FabricLoader.getInstance();

        if (instance.isModLoaded("plasmovoice")) {
            su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new PlasmoVoiceAddon());
        }

    }

}
