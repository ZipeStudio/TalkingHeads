package me.zipestudio.talkingheads;

import lombok.Getter;
import me.zipestudio.talkingheads.config.THConfig;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.slf4j.*;

import net.fabricmc.api.ModInitializer;

public class THServer implements ModInitializer {

    public static final String MOD_NAME = /*$ mod_name*/ "Talking Heads";
    public static final String MOD_ID = /*$ mod_id*/ "talkingheads";
    public static final String MOD_VERSION = /*$ mod_version*/ "1.0.3";
    public static final String MOD_AUTHORS = /*$ mod_authors*/ "ZipeStudio";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static MutableText text(String path, Object... args) {
        return Text.translatable(String.format("%s.%s", MOD_ID, path), args);
    }

    @Override
    public void onInitialize() {

    }
}