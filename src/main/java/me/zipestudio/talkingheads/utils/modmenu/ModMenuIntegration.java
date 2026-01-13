package me.zipestudio.talkingheads.utils.modmenu;

import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.config.YACLConfigurationScreen;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return YACLConfigurationScreen.createScreen(parent);
	}

}