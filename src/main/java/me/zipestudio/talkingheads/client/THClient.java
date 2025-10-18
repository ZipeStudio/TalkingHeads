package me.zipestudio.talkingheads.client;

import lombok.Getter;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.config.LeafyConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class THClient implements ClientModInitializer {

    @Getter
    private static final LeafyConfig leafyConfig = LeafyConfig.getInstance();

    public static final Logger LOGGER = LoggerFactory.getLogger(THServer.MOD_NAME + "/Client");

    @Override
    public void onInitializeClient() {

        THKeybinding.register();

        FabricLoader instance = FabricLoader.getInstance();
        if (instance.isModLoaded("plasmovoice")) {
            su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new PlasmoVoiceAddon());
        }

    }

}
