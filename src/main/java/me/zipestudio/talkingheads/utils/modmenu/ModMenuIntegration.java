package me.zipestudio.talkingheads.utils.modmenu;

import net.minecraft.client.gui.screens.Screen;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;

public class ModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return YACLConfigurationScreen.createScreen(parent);
	}

}