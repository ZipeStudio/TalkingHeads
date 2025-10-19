package me.zipestudio.talkingheads.config;

import dev.isxander.yacl3.api.*;
import lombok.experimental.ExtensionMethod;
import me.zipestudio.talkingheads.utils.yacl.base.SimpleCategory;
import me.zipestudio.talkingheads.utils.yacl.base.SimpleGroup;
import me.zipestudio.talkingheads.utils.yacl.base.SimpleOption;
import me.zipestudio.talkingheads.utils.yacl.utils.SimpleContent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import me.zipestudio.talkingheads.utils.ModMenuUtils;
import me.zipestudio.talkingheads.utils.yacl.extension.SimpleOptionExtension;
import me.zipestudio.talkingheads.utils.yacl.screen.SimpleYACLScreen;

import java.util.function.Function;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

    private static final Function<Boolean, Text> ENABLED_OR_DISABLE_FORMATTER = ModMenuUtils.getEnabledOrDisabledFormatter();

    private YACLConfigurationScreen() {
        throw new IllegalStateException("Screen class");
    }

    public static Screen createScreen(Screen parent) {
        LeafyConfig defConfig = LeafyConfig.getNewInstance();
        LeafyConfig config = LeafyConfig.getInstance();

        return SimpleYACLScreen.startBuilder(parent, config::saveAsync)
                .categories(
                        getGeneralCategory(defConfig, config)
                )
                .build();
    }

    private static ConfigCategory getGeneralCategory(LeafyConfig defConfig, LeafyConfig config) {
        return SimpleCategory.startBuilder("general")
                .groups(
                        getGeneralGroup(defConfig, config),
                        getExperimentalGroup(defConfig, config)
                )
                .build();
    }

    private static OptionGroup getGeneralGroup(LeafyConfig defConfig, LeafyConfig config) {
        return SimpleGroup.startBuilder("general")
                .options(

                        // Enable Mod
                        SimpleOption.<Boolean>startBuilder("enableMod")
                                .withDescription(SimpleContent.NONE)
                                .withBinding(defConfig.isEnableMod(), config::isEnableMod, config::setEnableMod, true)
                                .withController(ENABLED_OR_DISABLE_FORMATTER)
                                .build(),

                        // Use PlasmoVoice
                        SimpleOption.<Boolean>startBuilder("use_plasmo_voice")
                                .withDescription(SimpleContent.NONE)
                                .withBinding(defConfig.isUsePlasmoVoice(), config::isUsePlasmoVoice, config::setUsePlasmoVoice, true)
                                .withController(ENABLED_OR_DISABLE_FORMATTER)
                                .build(),

                        // Use Simple Voice Chat
                        SimpleOption.<Boolean>startBuilder("use_simple_voice_chat")
                                .withDescription(SimpleContent.NONE)
                                .withBinding(defConfig.isUseSimpleVoiceChat(), config::isUseSimpleVoiceChat, config::setUseSimpleVoiceChat, true)
                                .withController(ENABLED_OR_DISABLE_FORMATTER)
                                .build(),

                        // Removed Volume (reverse interpolation)
                        SimpleOption.<Double>startBuilder("removedVolume")
                                .withDescription(SimpleContent.NONE)
                                .withController(-Double.MAX_VALUE, Double.MAX_VALUE, 0, false)
                                .withBinding(defConfig.getRemovedVolume(), config::getRemovedVolume, config::setRemovedVolume, true)
                                .build(),

                        // Scale X
                        SimpleOption.<Double>startBuilder("scaleX")
                                .withDescription(SimpleContent.NONE)
                                .withController(-Double.MAX_VALUE, Double.MAX_VALUE, 0, false)
                                .withBinding(defConfig.getScaleX(), config::getScaleX, config::setScaleX, true)
                                .build(),

                        // Scale Y
                        SimpleOption.<Double>startBuilder("scaleY")
                                .withDescription(SimpleContent.NONE)
                                .withController(-Double.MAX_VALUE, Double.MAX_VALUE, 0, false)
                                .withBinding(defConfig.getScaleY(), config::getScaleY, config::setScaleY, true)
                                .build(),

                        // Scale Z
                        SimpleOption.<Double>startBuilder("scaleZ")
                                .withDescription(SimpleContent.NONE)
                                .withController(-Double.MAX_VALUE, Double.MAX_VALUE, 0, false)
                                .withBinding(defConfig.getScaleZ(), config::getScaleZ, config::setScaleZ, true)
                                .build()

                )
                .build();
    }

    private static OptionGroup getExperimentalGroup(LeafyConfig defConfig, LeafyConfig config) {
        return SimpleGroup.startBuilder("experimental")
                .options(

                        SimpleOption.<Boolean>startBuilder("helmetHideWhileTalking")
                                .withDescription(SimpleContent.NONE)
                                .withController(ENABLED_OR_DISABLE_FORMATTER)
                                .withBinding(defConfig.isHelmetHideWhileTalking(), config::isHelmetHideWhileTalking, config::setHelmetHideWhileTalking, true)
                                .build(),

                        SimpleOption.<Double>startBuilder("helmetShowDelay")
                                .withDescription(SimpleContent.NONE)
                                .withController(-Double.MAX_VALUE, Double.MAX_VALUE, 0, false)
                                .withBinding(defConfig.getHelmetShowDelay(), config::getHelmetShowDelay, config::setHelmetShowDelay, true)
                                .build()

                )
                .build();
    }

}


