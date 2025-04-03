package me.zipestudio.talkingheads.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import dev.isxander.yacl3.gui.controllers.string.number.DoubleFieldController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> config().generateScreen(parent);
    }

    public static YetAnotherConfigLib config() {
        return YetAnotherConfigLib.create(THConfig.GSON, (def, config, builder) -> builder

                .title(Text.translatable("talkingheads.modmenu.title"))

                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("talkingheads.category.general"))

                        .group(OptionGroup.createBuilder()

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("talkingheads.option.enableMod"))
                                                .description(OptionDescription.of(Text.translatable("talkingheads.option.enableMod.desc")))
                                                .stateManager(StateManager.createSimple(def.isEnableMod(), config::isEnableMod, config::setEnableMod))
                                                .customController(BooleanController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("talkingheads.option.use_plasmo_voice"))
                                                .stateManager(StateManager.createSimple(def.isUsePlasmoVoice(), config::isUsePlasmoVoice, config::setUsePlasmoVoice))
                                                .customController(BooleanController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("talkingheads.option.use_simple_voice_chat"))
                                                .stateManager(StateManager.createSimple(def.isUseSimpleVoiceChat(), config::isUseSimpleVoiceChat, config::setUseSimpleVoiceChat))
                                                .customController(BooleanController::new)
                                                .build()
                                )

                                .build())

                        .group(OptionGroup.createBuilder()

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("talkingheads.option.removedVolume"))
                                                .description(OptionDescription.of(Text.translatable("talkingheads.option.removedVolume.desc")))
                                                .stateManager(StateManager.createSimple(def.getRemovedVolume(), config::getRemovedVolume, config::setRemovedVolume))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("talkingheads.option.scaleX"))
                                                .description(OptionDescription.of(Text.translatable("talkingheads.option.scaleX.desc")))
                                                .stateManager(StateManager.createSimple(def.getScaleX(), config::getScaleX, config::setScaleX))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("talkingheads.option.scaleY"))
                                                .description(OptionDescription.of(Text.translatable("talkingheads.option.scaleY.desc")))
                                                .stateManager(StateManager.createSimple(def.getScaleY(), config::getScaleY, config::setScaleY))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("talkingheads.option.scaleZ"))
                                                .description(OptionDescription.of(Text.translatable("talkingheads.option.scaleZ.desc")))
                                                .stateManager(StateManager.createSimple(def.getScaleZ(), config::getScaleZ, config::setScaleZ))
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )

                                .build())
                        .build()
                )

        );
    }

}
