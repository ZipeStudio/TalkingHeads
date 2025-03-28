package me.zipestudio.talkingheads.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import dev.isxander.yacl3.gui.controllers.string.number.DoubleFieldController;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> config().generateScreen(parent);
    }

    public static YetAnotherConfigLib config() {
        return YetAnotherConfigLib.create(THConfig.GSON, (def, config, builder) -> builder

                .title(Text.translatable("text.title"))

                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("text.category.general"))

                        .group(OptionGroup.createBuilder()

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.option.general.enableMod"))
                                                .description(OptionDescription.of(Text.translatable("text.option.general.enableMod.desc")))
                                                .stateManager(StateManager.createInstant(def.isEnableMod(), config::isEnableMod, config::setEnableMod))
                                                .customController(BooleanController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.option.general.use_plasmo_voice"))
                                                .stateManager(StateManager.createInstant(def.isUsePlasmoVoice(), config::isUsePlasmoVoice, config::setUsePlasmoVoice))
                                                .customController(BooleanController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.option.general.use_simple_voice_chat"))
                                                .stateManager(StateManager.createInstant(def.isUseSimpleVoiceChat(), config::isUseSimpleVoiceChat, config::setUseSimpleVoiceChat))
                                                .customController(BooleanController::new)
                                                .build()
                                )

                                .build())

                        .group(OptionGroup.createBuilder()

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("text.option.general.removedVolume"))
                                                .description(OptionDescription.of(Text.translatable("text.option.general.removedVolume.desc")))
                                                .stateManager(StateManager.createInstant(def.getRemovedVolume(), config::getRemovedVolume, config::setRemovedVolume))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("text.option.general.scaleX"))
                                                .description(OptionDescription.of(Text.translatable("text.option.general.scaleX.desc")))
                                                .stateManager(StateManager.createInstant(def.getScaleX(), config::getScaleX, config::setScaleX))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("text.option.general.scaleY"))
                                                .description(OptionDescription.of(Text.translatable("text.option.general.scaleY.desc")))
                                                .stateManager(StateManager.createInstant(def.getScaleY(), config::getScaleY, config::setScaleY))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("text.option.general.scaleZ"))
                                                .description(OptionDescription.of(Text.translatable("text.option.general.scaleZ.desc")))
                                                .stateManager(StateManager.createInstant(def.getScaleZ(), config::getScaleZ, config::setScaleZ))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .build())

                        .build()
                )
        );
    }

}
