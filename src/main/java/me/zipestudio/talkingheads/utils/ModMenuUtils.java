package me.zipestudio.talkingheads.utils;

import me.zipestudio.talkingheads.THServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

	public static Text getName(String key) {
		return THServer.text(key + ".name");
	}

	public static Text getDescription(String key) {
		return THServer.text(key + ".description");
	}

	public static Identifier getContentId(SimpleContent content, String contentId) {
		return THServer.id(String.format("textures/config/%s.%s", contentId, content.getFileExtension()));
	}

	public static Text getModTitle() {
		return THServer.text("modmenu.title");
	}

	public static Function<Boolean, Text> getEnabledOrDisabledFormatter() {
		return state -> THServer.text("modmenu.formatter.enabled_or_disabled." + state);
	}

	public static Text getNoConfigScreenMessage() {
		return THServer.text("modmenu.no_config_library_screen.message");
	}

	public static Text getOldConfigScreenMessage(String version) {
		return THServer.text("modmenu.old_config_library_screen.message", version, THServer.YACL_DEPEND_VERSION);
	}
}
