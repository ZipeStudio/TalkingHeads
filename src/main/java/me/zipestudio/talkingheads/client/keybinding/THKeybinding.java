package me.zipestudio.talkingheads.client.keybinding;

import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.THClient;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class THKeybinding {

    //? if >=1.21.9 {
    private static final KeyBinding.Category TALKING_HEADS_CATEGORY =
            KeyBinding.Category.create(THServer.id(THServer.MOD_ID));
    public static final KeyBinding THKEY_MOD_TOGGLE = new KeyBinding(
            THServer.MOD_ID + ".keybinding.modToggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            TALKING_HEADS_CATEGORY
    );
    public static final KeyBinding THKEY_SETTINGS_MENU = new KeyBinding(
            THServer.MOD_ID + ".keybinding.modSettings",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            TALKING_HEADS_CATEGORY
    );
    //?} else {
    /*public static final KeyBinding THKEY_MOD_TOGGLE = new KeyBinding(
            "${mod_id}.keybinding.modToggle",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H,
            THServer.MOD_NAME
    );
    public static final KeyBinding THKEY_SETTINGS_MENU = new KeyBinding(
            "${mod_id}.keybinding.modSettings",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            THServer.MOD_NAME
    );
    *///?}

    public static void register() {
        registerDefaultKeys();

        ClientTickEvents.START_CLIENT_TICK.register((client -> {

            if (THKEY_MOD_TOGGLE.wasPressed()) {
                if (client.player == null) {
                    return;
                }

                LeafyConfig leafyConfig = THClient.getLeafyConfig();
                boolean toggle = !leafyConfig.isEnableMod();
                leafyConfig.setEnableMod(toggle);

                client.player.sendMessage(
                        Text.translatable(THServer.MOD_NAME)
                                .append(" ")
                                .append(Text.translatable(THServer.MOD_ID + ".keybinding.modToggle.actionbar." + toggle)),
                        true
                );
            }

            if (THKEY_SETTINGS_MENU.wasPressed()) {
                if (client.player == null) {
                    return;
                }

                client.setScreen(YACLConfigurationScreen.createScreen(client.currentScreen));
            }

        }));
    }

    private static void registerDefaultKeys() {
        registerKeyBinding(THKEY_MOD_TOGGLE);
        registerKeyBinding(THKEY_SETTINGS_MENU);
    }

    public static void registerKeyBinding(KeyBinding keyBinding) {
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

}
