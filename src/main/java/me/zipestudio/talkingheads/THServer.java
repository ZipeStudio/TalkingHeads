package me.zipestudio.talkingheads;

import lombok.Getter;
import me.zipestudio.talkingheads.config.LeafyConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.*;

public class THServer {

    public static final String MOD_NAME = /*$ mod_name*/ "Talking Heads";
    public static final String MOD_ID = /*$ mod_id*/ "talkingheads";
    public static final String YACL_DEPEND_VERSION = /*$ yacl*/ "3.8.0+1.21.9-fabric";
    public static final String MOD_VERSION = /*$ mod_version*/ "1.1.3+1.21.10+fabric";
    public static final String MOD_AUTHORS = /*$ mod_authors*/ "ZipeStudio";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation id(String path) {
        //? if >=1.21 {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
         //?} else {
        /*return ResourceLocation.tryBuild(MOD_ID, path);
        *///?}
    }

    public static MutableComponent text(String path, Object... args) {
        return Component.translatable(String.format("%s.%s", MOD_ID, path), args);
    }

    public static void onInitialize() {
        THClient.initial();
        LOGGER.info("{} Initialized", THServer.MOD_NAME);
    }

}