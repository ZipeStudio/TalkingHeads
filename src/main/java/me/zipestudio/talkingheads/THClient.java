package me.zipestudio.talkingheads;

import com.mojang.authlib.GameProfile;
import lombok.Getter;
import me.zipestudio.talkingheads.config.LeafyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;

public class THClient {

    public static final Set<UUID> AUTHORS = Set.of(
            UUID.fromString("192a434a-83dd-447b-b437-89d403ffc770")
    );

    public static final Set<String> AUTHOR_NAMES = Set.of(
            "ZipeStudio"
    );

    @Getter
    private static LeafyConfig leafyConfig;

    public static final Logger LOGGER = LoggerFactory.getLogger(THServer.MOD_NAME + "/Client");

    public static void initial() {
        leafyConfig = LeafyConfig.getInstance();
    }

    public static void onInitializeClient() {
        THClient.initial();
        LOGGER.info("{} Client Initialized", THServer.MOD_NAME);
    }

    public static boolean hasDevCape(GameProfile profile) {
        //? if >=1.21.9 {
        return AUTHORS.contains(profile.id()) || AUTHOR_NAMES.contains(profile.name());
        //?} else {
        /*return AUTHORS.contains(profile.getId()) || AUTHOR_NAMES.contains(profile.getName());
        *///?}
    }

}