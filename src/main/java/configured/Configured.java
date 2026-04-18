package configured;

import dev.xpple.betterconfig.api.BetterConfigAPI;
import dev.xpple.betterconfig.api.ModConfigBuilder;

import net.fabricmc.api.DedicatedServerModInitializer;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Configured implements DedicatedServerModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("configured");
	public static MinecraftServer MC_SERVER = null;


	@Override
	public void onInitializeServer() {
		new ModConfigBuilder<CommandSource, CommandBuildContext>("configured", Settings.class).build();


		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(
				(server, resourceManager ,success) ->
						BetterConfigAPI.getInstance().getModConfig("configured").reload()
		);


		LOGGER.info("Minecraft is now more configurable...");
	}







}