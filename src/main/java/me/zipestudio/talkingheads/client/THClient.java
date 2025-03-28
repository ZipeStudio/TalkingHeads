package me.zipestudio.talkingheads.client;

import lombok.Getter;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.config.THConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class THClient implements ClientModInitializer {

    @Getter
    private static THConfig config;

    @Override
    public void onInitializeClient() {

        if (THConfig.GSON.load()) {
            config = THConfig.GSON.instance();
        }

        THKeybinding.register();

        FabricLoader instance = FabricLoader.getInstance();
        if (instance.isModLoaded("plasmovoice")) {
            su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new PlasmoVoiceAddon());
        }

    }

}
