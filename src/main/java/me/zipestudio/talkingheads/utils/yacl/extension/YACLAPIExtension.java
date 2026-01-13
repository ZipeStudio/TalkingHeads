package me.zipestudio.talkingheads.utils.yacl.extension;

import dev.isxander.yacl3.api.*;

import me.zipestudio.talkingheads.THServer;

import java.util.*;

import me.zipestudio.talkingheads.utils.yacl.state.PreviewStateManager;

@SuppressWarnings("unused")
public class YACLAPIExtension {

    public static <A> ListOption.Builder<A> bindingE(ListOption.Builder<A> builder, Binding<List<A>> binding, boolean instant) {
        builder.state(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
        return builder;
    }

    public static <A> Option.Builder<A> bindingE(Option.Builder<A> builder, Binding<A> binding, boolean instant) {
        builder.stateManager(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
        return builder;
    }
}
