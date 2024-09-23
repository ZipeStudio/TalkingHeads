package me.zipestudio.talkingheads.modmenu;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import me.zipestudio.talkingheads.client.THClient;
import me.zipestudio.talkingheads.client.THManager;
import me.zipestudio.talkingheads.config.THConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ModMenuIntegrationScreen {
    public static Screen createScreen(Screen parentScreen) {
        THConfig config = THConfig.getInstance();
        THConfig configDefault = new THConfig(true);

        MutableText modmenuTitle = Text.translatable("talking-heads.mod_menu.title");

        ConfigBuilder configBuilder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(modmenuTitle)
                .setSavingRunnable(() -> {
                    config.save();
                    THManager.setClientConfig(config);
                });

        ConfigCategory category = configBuilder.getOrCreateCategory(modmenuTitle);
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

        category.addEntry(entryBuilder.startBooleanToggle(Text.translatable("talking-heads.mod_menu.toggle"), config.isToggle())
                .setDefaultValue(configDefault.isToggle())
                .setSaveConsumer(config::setToggle)
                .build());

        return configBuilder.build();
    }

}
