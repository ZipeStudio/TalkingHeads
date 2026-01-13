package me.zipestudio.talkingheads;

import lombok.Getter;
import me.zipestudio.talkingheads.config.LeafyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;

public class THClient {

    public static final Set<UUID> AUTHORS = Set.of(UUID.fromString("192a434a-83dd-447b-b437-89d403ffc770"));

    @Getter
    private static LeafyConfig leafyConfig;

    public static final Logger LOGGER = LoggerFactory.getLogger(THServer.MOD_NAME + "/Client");

    public static void initial() {
        leafyConfig = LeafyConfig.getInstance();
    }

}
