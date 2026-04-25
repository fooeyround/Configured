package configured;

import com.mojang.authlib.GameProfile;
import configured.util.GameProfileAdapter;
import dev.xpple.betterconfig.api.BetterConfigAPI;
import dev.xpple.betterconfig.api.ModConfigBuilder;

import net.fabricmc.api.DedicatedServerModInitializer;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Configured implements DedicatedServerModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("configured");
	public static MinecraftServer MC_SERVER = null;


	@Override
	public void onInitializeServer() {
		new ModConfigBuilder<CommandSource, CommandBuildContext>("configured", Settings.class)
				.registerTypeHierarchy(GameProfileArgument.Result.class, new GameProfileAdapter(), GameProfileArgument::gameProfile)
				.build();


		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(
				(_, _, _) ->
						BetterConfigAPI.getInstance().getModConfig("configured").reload()
		);


		LOGGER.info("Minecraft is now more configurable...");
	}







}