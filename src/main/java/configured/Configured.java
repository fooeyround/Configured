package configured;

import dev.xpple.betterconfig.api.ModConfigBuilder;
import net.fabricmc.api.DedicatedServerModInitializer;


import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Configured implements DedicatedServerModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("configured");
	public static MinecraftServer MC_SERVER = null;


	@Override
	public void onInitializeServer() {
		new ModConfigBuilder<ServerCommandSource, CommandRegistryAccess>("configured", Settings.class).build();


		LOGGER.info("Minecraft is now more configurable...");
	}







}