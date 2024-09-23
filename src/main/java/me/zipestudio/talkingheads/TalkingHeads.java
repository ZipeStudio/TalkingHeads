package me.zipestudio.talkingheads;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TalkingHeads implements ModInitializer {
	public static final String MOD_NAME = /*$ mod_name*/ "Talking Heads";
	public static final String MOD_ID = /*$ mod_id*/  "talking-heads";
	public static final String MOD_VERSION = /*$ mod_version*/  "1.0.1";
	public static final String MOD_AUTHORS = /*$ mod_authors*/  "ZipeStudio";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}