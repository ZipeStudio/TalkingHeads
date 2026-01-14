package me.zipestudio.talkingheads.entrypoint;

//? if neoforge {

/*import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.PlasmoVoiceAddon;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import me.zipestudio.talkingheads.utils.modmenu.ModMenuIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = THServer.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeClientEntrypoint {

    public NeoForgeClientEntrypoint(IEventBus bus, ModContainer container) {
        THClient.initial();
        bus.addListener(this::registerKeybindings);

        ModMenuIntegration integration = new ModMenuIntegration();
        integration.register(container);

        if (isModLoaded("plasmovoice", true)) {
            su.plo.voice.api.client.PlasmoVoiceClient.getAddonsLoader().load(new PlasmoVoiceAddon());
        }
    }

    private void registerKeybindings(RegisterKeyMappingsEvent event) {
        event.register(THKeybinding.THKEY_MOD_TOGGLE);
        event.register(THKeybinding.THKEY_SETTINGS_MENU);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {

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
            client.setScreen(
                    YACLConfigurationScreen.createScreen(client.screen)
            );
        }
    }

    private boolean isModLoaded(String modid, boolean loadingPhase) {
        if (loadingPhase) {
            //? if >=1.21.9 {
            return FMLLoader.getCurrent().getLoadingModList().getModFileById(modid) != null;
            //?} else {
            /^return FMLLoader.getLoadingModList().getModFileById(modid) != null;
            ^///?}
        } else {
            return ModList.get().isLoaded(modid);
        }
    }
}

*///?}