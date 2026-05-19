package me.zipestudio.talkingheads.entrypoint;

//? if fabric {

import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import net.fabricmc.api.ModInitializer;

public class CommonEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		THServer.onInitialize();
	}

}

//?} elif neoforge {

/*import me.zipestudio.talkingheads.THClient;
import me.zipestudio.talkingheads.THServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(THServer.MOD_ID)
public class CommonEntrypoint {

    public CommonEntrypoint(IEventBus bus, ModContainer container) {
		THServer.onInitialize();
    }

}

*///?} elif forge {

/*import me.zipestudio.talkingheads.THServer;
import me.zipestudio.talkingheads.client.keybinding.THKeybinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(THServer.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		THServer.onInitialize();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientEntrypoint::onInitializeClient);
	}

}

*///?}