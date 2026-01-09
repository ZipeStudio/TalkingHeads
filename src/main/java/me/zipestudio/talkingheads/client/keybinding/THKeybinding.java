package me.zipestudio.talkingheads.client.keybinding;

import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.THClient;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class THKeybinding {

    //? if >=1.21.9 {
    private static final KeyMapping.Category TALKING_HEADS_CATEGORY = KeyMapping.Category.register(THServer.id(THServer.MOD_ID));

    public static final KeyMapping THKEY_MOD_TOGGLE = new KeyMapping(
            THServer.MOD_ID + ".keybinding.modToggle",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            TALKING_HEADS_CATEGORY
    );
    public static final KeyMapping THKEY_SETTINGS_MENU = new KeyMapping(
            THServer.MOD_ID + ".keybinding.modSettings",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            TALKING_HEADS_CATEGORY
    );
    //?} else {
    /*public static final KeyMapping THKEY_MOD_TOGGLE = new KeyMapping(
            THServer.MOD_ID + ".keybinding.modToggle",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H,
            THServer.MOD_NAME
    );
    public static final KeyMapping THKEY_SETTINGS_MENU = new KeyMapping(
            THServer.MOD_ID + ".keybinding.modSettings",
            InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(),
            THServer.MOD_NAME
    );
    *///?}

    public static void register() {
        registerDefaultKeys();

        ClientTickEvents.START_CLIENT_TICK.register((client -> {

            if (THKEY_MOD_TOGGLE.consumeClick()) {
                if (client.player == null) {
                    return;
                }

                LeafyConfig leafyConfig = THClient.getLeafyConfig();
                boolean toggle = !leafyConfig.isEnableMod();
                leafyConfig.setEnableMod(toggle);

                client.player.displayClientMessage(
                        Component.translatable(THServer.MOD_NAME)
                                .append(" ")
                                .append(Component.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle)),
                        true
                );
            }

            if (THKEY_SETTINGS_MENU.consumeClick()) {
                if (client.player == null) {
                    return;
                }

                client.setScreen(YACLConfigurationScreen.createScreen(client.screen));
            }

        }));
    }

    private static void registerDefaultKeys() {
        registerKeyBinding(THKEY_MOD_TOGGLE);
        registerKeyBinding(THKEY_SETTINGS_MENU);
    }

    public static void registerKeyBinding(KeyMapping keyBinding) {
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

}
