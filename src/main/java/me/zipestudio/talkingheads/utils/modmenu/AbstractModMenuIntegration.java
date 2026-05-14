package me.zipestudio.talkingheads.utils.modmenu;

//? if fabric {
import com.terraformersmc.modmenu.api.*;
import me.zipestudio.talkingheads.THServer;
import net.fabricmc.loader.api.*;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screens.Screen;

public abstract class AbstractModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		FabricLoader fabricLoader = FabricLoader.getInstance();
		if (fabricLoader.isModLoaded("yet_another_config_lib_v3")) {
			ModContainer modContainer = fabricLoader.getModContainer("yet_another_config_lib_v3").orElseThrow();
			Version version = modContainer.getMetadata().getVersion();
			try {
				Version requestsVersion = Version.parse(THServer.YACL_DEPEND_VERSION);
				if (version.compareTo(requestsVersion) >= 0) {
					return this::createConfigScreen;
				}
			} catch (VersionParsingException e) {
				THServer.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
			}
			return parent -> NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getFriendlyString());
		}
		return NoConfigLibraryScreen::createScreen;
	}

	protected abstract Screen createConfigScreen(Screen parent);
}

//?} elif neoforge {

/*import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import net.neoforged.fml.*;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.loading.FMLLoader;
import org.apache.maven.artifact.versioning.*;

//? if >=1.21 {
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
 //?} else {
/^import net.neoforged.neoforge.client.ConfigScreenHandler;
^///?}

public abstract class AbstractModMenuIntegration {

	public void register(ModContainer container) {

		//? if >=1.21 {

		container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> {
			if (isModLoaded("yet_another_config_lib_v3", false)) {
			ModContainer yacl = ModList.get().getModContainerById("yet_another_config_lib_v3").orElseThrow();
			ArtifactVersion version = yacl.getModInfo().getVersion();
			try {
				ArtifactVersion requestsVersion = new DefaultArtifactVersion(THServer.YACL_DEPEND_VERSION);
				if (version.compareTo(requestsVersion) >= 0) {
					return this.createConfigScreen(parent);
				}
			} catch (Exception e) {
				THClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
			}
			return NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getQualifier());
		}
		return NoConfigLibraryScreen.createScreen(parent);
	});

	//?} else {

		/^container.registerExtensionPoint(
				ConfigScreenHandler.ConfigScreenFactory.class,
				() -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> {

					if (isModLoaded("yet_another_config_lib_v3", false)) {
						ModContainer yacl = ModList.get().getModContainerById("yet_another_config_lib_v3").orElseThrow();
						ArtifactVersion version = yacl.getModInfo().getVersion();

						try {
							ArtifactVersion requestsVersion = new DefaultArtifactVersion(THServer.YACL_DEPEND_VERSION);

							if (version.compareTo(requestsVersion) >= 0) {
								return this.createConfigScreen(parent);
							}
						} catch (Exception e) {
							THClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
						}

						return NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getQualifier());
					}

					return NoConfigLibraryScreen.createScreen(parent);
				})
		);

		^///?}

	}

	protected abstract Screen createConfigScreen(Screen parent);

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		if (loadingPhase) {
			//? if >=1.21.9 {
			/^return FMLLoader.getCurrent().getLoadingModList().getModFileById(modid) != null;
			 ^///?} else {
			return FMLLoader.getLoadingModList().getModFileById(modid) != null;
			//?}
		} else {
			return ModList.get().isLoaded(modid);
		}
	}

}

*///?} elif forge {

/*import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.maven.artifact.versioning.*;

public abstract class AbstractModMenuIntegration {

	public void register(ModContainer container) {
		container.registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory((minecraft, parent) -> {
			if (isModLoaded("yet_another_config_lib_v3", false)) {
				ModContainer yacl = ModList.get().getModContainerById("yet_another_config_lib_v3").orElseThrow();
				ArtifactVersion version = yacl.getModInfo().getVersion();
				try {
					ArtifactVersion requestsVersion = new DefaultArtifactVersion(THServer.YACL_DEPEND_VERSION);
					if (version.compareTo(requestsVersion) >= 0) {
						return this.createConfigScreen(parent);
					}
				} catch (Exception e) {
					THClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
				}
				return NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getQualifier());
			}
			return NoConfigLibraryScreen.createScreen(parent);
		}));
	}

	protected abstract Screen createConfigScreen(Screen parent);

    public static boolean isModLoaded(String modid, boolean loadingPhase) {
        if (loadingPhase) {
            return FMLLoader.getLoadingModList().getModFileById(modid) != null;
        } else {
            return ModList.get().isLoaded(modid);
        }
    }

}

*///?}