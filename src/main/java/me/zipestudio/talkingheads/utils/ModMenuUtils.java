package me.zipestudio.talkingheads.utils;

import me.zipestudio.talkingheads.THServer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import me.zipestudio.talkingheads.utils.yacl.utils.SimpleContent;

import java.util.function.Function;

public class ModMenuUtils {

	public static String getOptionKey(String optionId) {
		return String.format("modmenu.option.%s", optionId);
	}

	public static String getCategoryKey(String categoryId) {
		return String.format("modmenu.category.%s", categoryId);
	}

	public static String getGroupKey(String groupId) {
		return String.format("modmenu.group.%s", groupId);
	}

	public static Component getName(String key) {
		return THServer.text(key + ".name");
	}

	public static Component getDescription(String key) {
		return THServer.text(key + ".description");
	}

	public static ResourceLocation getContentId(SimpleContent content, String contentId) {
		return THServer.id(String.format("textures/config/%s.%s", contentId, content.getFileExtension()));
	}

	public static Component getModTitle() {
		return THServer.text("modmenu.title");
	}

	public static Function<Boolean, Component> getEnabledOrDisabledFormatter() {
		return state -> THServer.text("modmenu.formatter.enabled_or_disabled." + state);
	}

	public static Component getNoConfigScreenMessage() {
		return THServer.text("modmenu.no_config_library_screen.message");
	}

	public static Component getOldConfigScreenMessage(String version) {
		return THServer.text("modmenu.old_config_library_screen.message", version, THServer.YACL_DEPEND_VERSION);
	}
}
