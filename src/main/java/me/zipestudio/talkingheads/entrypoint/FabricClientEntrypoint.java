package me.zipestudio.talkingheads.entrypoint;

//? if fabric {

//? if >=1.21.9 {

import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.PlasmoVoiceAddon;
import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.client.THManager;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.utils.modmenu.NoConfigLibraryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//?}

import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.PlasmoVoiceAddon;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import me.zipestudio.talkingheads.utils.modmenu.NoConfigLibraryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricClientEntrypoint implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(THServer.MOD_NAME + "/Client");
	private static final LeafyConfig LEAFY_CONFIG = LeafyConfig.getInstance();

	@Override
	public void onInitializeClient() {
		THClient.initial();
		registerKeybinding();

		FabricLoader instance = FabricLoader.getInstance();
		if (instance.isModLoaded("plasmovoice")) {
			su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new PlasmoVoiceAddon());
		}
	}

	public static void registerKeybinding() {
		registerDefaultKeys();

		ClientTickEvents.START_CLIENT_TICK.register((client -> {

			if (THKeybinding.THKEY_MOD_TOGGLE.consumeClick()) {
				if (client.player == null) {
					return;
				}

				boolean toggle = !LEAFY_CONFIG.isEnableMod();
				LEAFY_CONFIG.setEnableMod(toggle);

				client.player.displayClientMessage(
						Component.translatable(THServer.MOD_NAME)
								.append(" ")
								.append(Component.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle)),
						true
				);
			}

			if (THKeybinding.THKEY_SETTINGS_MENU.consumeClick()) {

				if (client.player == null) {
					return;
				}

				if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
					client.setScreen(YACLConfigurationScreen.createScreen(client.screen));
				} else {
					client.setScreen(NoConfigLibraryScreen.createScreen(client.screen));
				}
			}
		}));
	}

	private static void registerDefaultKeys() {
		registerKeyBinding(THKeybinding.THKEY_MOD_TOGGLE);
		registerKeyBinding(THKeybinding.THKEY_SETTINGS_MENU);
	}

	public static void registerKeyBinding(KeyMapping keyBinding) {
		KeyBindingHelper.registerKeyBinding(keyBinding);
	}

}

//?}
