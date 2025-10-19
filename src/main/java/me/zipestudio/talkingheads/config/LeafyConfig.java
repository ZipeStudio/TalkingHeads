package me.zipestudio.talkingheads.config;

import lombok.*;
import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.utils.CodecUtils;
import me.zipestudio.talkingheads.utils.ConfigUtils;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.concurrent.CompletableFuture;

import static me.zipestudio.talkingheads.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class LeafyConfig {

	public static final Codec<LeafyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("enableMod", true, Codec.BOOL, LeafyConfig::isEnableMod),
			option("usePlasmoVoice", true, Codec.BOOL, LeafyConfig::isUsePlasmoVoice),
			option("useSimpleVoiceChat", true, Codec.BOOL, LeafyConfig::isUseSimpleVoiceChat),
			option("removedVolume", 0.0020, Codec.DOUBLE, LeafyConfig::getRemovedVolume),
			option("scaleX", 0.3, Codec.DOUBLE, LeafyConfig::getScaleX),
			option("scaleY", 0.3, Codec.DOUBLE, LeafyConfig::getScaleY),
			option("scaleZ", 0.3, Codec.DOUBLE, LeafyConfig::getScaleZ),
			option("helmetHideWhileTalking", false, Codec.BOOL, LeafyConfig::isHelmetHideWhileTalking),
			option("helmetShowDelay", 0.1, Codec.DOUBLE, LeafyConfig::getHelmetShowDelay)
	).apply(instance, LeafyConfig::new));

	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(THServer.MOD_ID + ".json5").toFile();
	private static final Logger LOGGER = LoggerFactory.getLogger(THServer.MOD_NAME + "/Config");
	private static LeafyConfig INSTANCE;

	private boolean enableMod;
	private boolean usePlasmoVoice;
	private boolean useSimpleVoiceChat;
	private double removedVolume;
	private double scaleX;
	private double scaleY;
	private double scaleZ;
	private boolean helmetHideWhileTalking;
	private double helmetShowDelay;

	private LeafyConfig() {
		throw new IllegalArgumentException();
	}

	public static LeafyConfig getInstance() {
		return INSTANCE == null ? reload() : INSTANCE;
	}

	public static LeafyConfig reload() {
		return INSTANCE = LeafyConfig.read();
	}

	public static LeafyConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	private static LeafyConfig read() {
		return ConfigUtils.readConfig(CODEC, CONFIG_FILE, LOGGER);
	}

	public void saveAsync() {
		CompletableFuture.runAsync(this::save);
	}

	public void save() {
		ConfigUtils.saveConfig(this, CODEC, CONFIG_FILE, LOGGER);
	}
}
