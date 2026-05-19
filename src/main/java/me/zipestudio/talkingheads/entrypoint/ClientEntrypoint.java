package me.zipestudio.talkingheads.entrypoint;

//? if fabric {

//? if >=26.1 {
/*import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
*///?} else {
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//?}

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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientEntrypoint implements ClientModInitializer {

	private static final LeafyConfig LEAFY_CONFIG = LeafyConfig.getInstance();

	@Override
	public void onInitializeClient() {
        THClient.onInitializeClient();
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

				//? if >=26.1 {
				/*client.getChatListener().handleOverlay(
						Component.translatable(THServer.MOD_NAME)
								.append(" ")
								.append(Component.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle))
				);
				*///?} else {
				client.player.displayClientMessage(
						Component.translatable(THServer.MOD_NAME)
								.append(" ")
								.append(Component.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle)),
						true
				);
				//?}

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

		//? if >=26.1 {
		/*KeyMappingHelper.registerKeyMapping(keyBinding);
		*///?} else {
		KeyBindingHelper.registerKeyBinding(keyBinding);
		//?}

	}

}

//?} elif neoforge {

/*import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.utils.modmenu.AbstractModMenuIntegration;
import me.zipestudio.talkingheads.utils.modmenu.ModMenuIntegration;
import me.zipestudio.talkingheads.utils.modmenu.NoConfigLibraryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = THServer.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = THServer.MOD_ID, value = Dist.CLIENT)
public class ClientEntrypoint {

    public ClientEntrypoint(IEventBus bus, ModContainer container) {

        THClient.onInitializeClient();

        ModMenuIntegration integration = new ModMenuIntegration();
        integration.register(container);

        //? if !(neoforge && <1.21) {
        if (AbstractModMenuIntegration.isModLoaded("plasmovoice", true)) {
            su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new me.zipestudio.talkingheads.client.PlasmoVoiceAddon());
        }
        //?}
    }

    @SubscribeEvent
    static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(THKeybinding.THKEY_MOD_TOGGLE);
        event.register(THKeybinding.THKEY_SETTINGS_MENU);
    }

    @SubscribeEvent
    public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Pre event) {

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        if (THKeybinding.THKEY_MOD_TOGGLE.consumeClick()) {

            LeafyConfig leafyConfig = THClient.getLeafyConfig();
            boolean toggle = !leafyConfig.isEnableMod();
            leafyConfig.setEnableMod(toggle);

            //? if >=26.1 {
            /^client.getChatListener().handleOverlay(
                    Component.translatable(THServer.MOD_NAME)
                            .append(" ")
                            .append(Component.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle))
            );
            ^///?} else {
            client.player.displayClientMessage(
                    Component.translatable(THServer.MOD_NAME)
                            .append(" ")
                            .append(Component.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle)),
                    true
            );
            //?}
        }

        if (THKeybinding.THKEY_SETTINGS_MENU.consumeClick()) {
            if (AbstractModMenuIntegration.isModLoaded("yet_another_config_lib_v3", true)) {
                client.setScreen(YACLConfigurationScreen.createScreen(client.screen));
            } else {
                client.setScreen(NoConfigLibraryScreen.createScreen(client.screen));
            }
        }
    }

}

*///?} elif forge {

/*import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import me.zipestudio.talkingheads.utils.modmenu.AbstractModMenuIntegration;
import me.zipestudio.talkingheads.utils.modmenu.ModMenuIntegration;
import me.zipestudio.talkingheads.utils.modmenu.NoConfigLibraryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = THServer.MOD_ID, value = Dist.CLIENT)
public class ClientEntrypoint {

	public static void onInitializeClient() {

		THClient.onInitializeClient();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(ClientEntrypoint::onRegisterKeyMappings);

		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(ModLoadingContext.get().getContainer());

		if (AbstractModMenuIntegration.isModLoaded("plasmovoice", true)) {
			su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new me.zipestudio.talkingheads.client.PlasmoVoiceAddon());
		}

	}

	private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(THKeybinding.THKEY_MOD_TOGGLE);
		event.register(THKeybinding.THKEY_SETTINGS_MENU);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {

		Minecraft client = Minecraft.getInstance();
		if (client.player == null) return;

		if (THKeybinding.THKEY_MOD_TOGGLE.consumeClick()) {

			LeafyConfig leafyConfig = THClient.getLeafyConfig();
			boolean toggle = !leafyConfig.isEnableMod();
			leafyConfig.setEnableMod(toggle);

			client.player.displayClientMessage(
					Component.translatable(THServer.MOD_NAME)
							.append(" ")
							.append(Component.translatable(
									THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle
							)),
					true
			);
		}

		if (THKeybinding.THKEY_SETTINGS_MENU.consumeClick()) {
			if (AbstractModMenuIntegration.isModLoaded("yet_another_config_lib_v3", true)) {
				client.setScreen(YACLConfigurationScreen.createScreen(client.screen));
			} else {
				client.setScreen(NoConfigLibraryScreen.createScreen(client.screen));
			}
		}

	}

}

*///?}