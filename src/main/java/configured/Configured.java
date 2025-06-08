package configured;

import dev.xpple.betterconfig.api.ModConfigBuilder;
import net.fabricmc.api.ModInitializer;


import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Configured implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("configured");
	public static MinecraftServer MC_SERVER = null;


	@Override
	public void onInitialize() {
		new ModConfigBuilder<>("configured", Settings.class)
//				.registerTypeHierarchy(UUID.class, new UUIDAdapter(), UuidArgumentType::new)
				.build();
//		new ModConfigBuilder("configured-fixes", Fixes.class).build();

		LOGGER.info("Minecraft is now more configurable...");
	}







}