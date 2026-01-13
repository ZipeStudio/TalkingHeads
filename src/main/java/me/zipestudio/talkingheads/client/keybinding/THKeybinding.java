package me.zipestudio.talkingheads.client.keybinding;

import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.config.LeafyConfig;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
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

}
