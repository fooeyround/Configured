package configured;

import dev.xpple.betterconfig.api.ModConfigBuilder;
import net.fabricmc.api.ModInitializer;


//#if MC >= 11900

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//#else
//$$ import org.apache.logging.log4j.LogManager;
//$$ import org.apache.logging.log4j.Logger;
//#endif



public class Configured implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = getLogger("configurable");


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		new ModConfigBuilder("configured", Settings.class).build();
//
//
//		new ModConfigBuilder("configured-fixes", Fixes.class).build();




		LOGGER.info("Minecraft is now more configurable!");
	}


	public static Logger getLogger(String name) {
		//#if MC >= 11900
		return LoggerFactory.getLogger(name);

		//#else
		//$$ return LogManager.getLogger(name);
		//#endif
	}




}