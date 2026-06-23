package me.zipestudio.talkingheads.utils.modmenu;

import com.google.common.collect.Sets;
import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.utils.ModMenuUtils;
import net.minecraft.SharedConstants;
import net.minecraft.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import java.net.*;
import java.util.*;

import net.minecraft.util.*;
import org.jetbrains.annotations.*;

public class NoConfigLibraryScreen {

	private static final Set<String> ALLOWED_PROTOCOLS = Sets.newHashSet("http", "https");
	private static final String YACL_MODRINTH_LINK = "https://modrinth.com/mod/yacl/versions";

	private NoConfigLibraryScreen() {
		throw new IllegalStateException("Screen class, use createScreen(...) method!");
	}

	public static @NotNull Screen createScreen(Screen parent) {
		return new ConfirmScreen((open) -> NoConfigLibraryScreen.onConfirm(open, parent), ModMenuUtils.getModTitle(), ModMenuUtils.getNoConfigScreenMessage(), CommonComponents.GUI_CONTINUE, CommonComponents.GUI_BACK);
	}

	private static void onConfirm(boolean open, Screen parent) {
		if (open) {
			try {
				String url = NoConfigLibraryScreen.YACL_MODRINTH_LINK + SharedConstants.getCurrentVersion()./*? if >=1.21.6 {*/ name() /*?} else {*//*getName()*//*?}*/;
				URI link = new URI(url);
				String string = link.getScheme();
				if (string == null) {
					throw new URISyntaxException(url, "Missing protocol");
				}
				if (!NoConfigLibraryScreen.ALLOWED_PROTOCOLS.contains(string.toLowerCase(Locale.ROOT))) {
					throw new URISyntaxException(url, "Unsupported protocol: " + string.toLowerCase(Locale.ROOT));
				}
				Util.getPlatform().openUri(link);
			} catch (URISyntaxException e) {
				THClient.LOGGER.error("Can't open YACL Modrinth page:", e);
			}
		} else {
			THClient.setScreen(Minecraft.getInstance(), parent);
		}
	}

	public static Screen createScreenAboutOldVersion(Screen parent, String version) {
		return new ConfirmScreen((open) -> NoConfigLibraryScreen.onConfirm(open, parent), ModMenuUtils.getModTitle(), ModMenuUtils.getOldConfigScreenMessage(version), CommonComponents.GUI_CONTINUE, CommonComponents.GUI_BACK);
	}
}