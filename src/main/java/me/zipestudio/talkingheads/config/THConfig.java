package me.zipestudio.talkingheads.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import me.zipestudio.talkingheads.TalkingHeads;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Getter @Setter
public class THConfig {
    public static final Codec<THConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("toggle").forGetter(THConfig::isToggle)
    ).apply(instance, THConfig::new));

    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(TalkingHeads.MOD_ID + ".json5").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkingHeads.MOD_NAME + "/Config");

    private boolean toggle = true;

    public THConfig() {

    }

    public THConfig(boolean toggle) {
        this.toggle = toggle;
    }

    public static THConfig getInstance() {
        return THConfig.read();
    }

    private static @NotNull THConfig create() {
        THConfig config = new THConfig();
        try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
            String json = GSON.toJson(CODEC.encode(config, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).getOrThrow());
            writer.write(json);
        } catch (Exception e) {
            LOGGER.error("Failed to create config", e);
        }
        return config;
    }

    private static THConfig read() {
        if (!CONFIG_FILE.exists()) {
            return THConfig.create();
        }

        try (FileReader reader = new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
            return CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader)).getOrThrow().getFirst();
        } catch (Exception e) {
            LOGGER.error("Failed to read config", e);
        }
        return THConfig.create();
    }

    public void save() {
        CompletableFuture.runAsync(() -> {
            try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
                String json = GSON.toJson(CODEC.encode(this, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).getOrThrow());
                writer.write(json);
            } catch (Exception e) {
                LOGGER.error("Failed to save config", e);
            }
        });
    }

}
