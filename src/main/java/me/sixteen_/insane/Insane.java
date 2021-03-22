package me.sixteen_.insane;

import me.sixteen_.insane.command.CommandManager;
import me.sixteen_.insane.config.Config;
import me.sixteen_.insane.module.ModuleManager;
import me.sixteen_.insane.ntrfc.IMinecraftClient;
import me.sixteen_.insane.util.Logger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Insane implements ClientModInitializer {

	// Instance
	private static Insane insane;
	// Client information
	private final String clientName, clientVersion;
	private boolean loadConfig;
	// Logger
	private final Logger logger;
	// Managers
	private final ModuleManager moduleManager;
	private final CommandManager commandManager;
	// Config
	private final Config config;
	// Minecraft
	private final IMinecraftClient imc;

	@Override
	public final void onInitializeClient() {
	}

	public Insane() {
		if (insane == null) {
			insane = this;
		}
		clientName = "Insane";
		clientVersion = "b2";
		loadConfig = true;
		imc = (IMinecraftClient) MinecraftClient.getInstance();
		logger = new Logger();
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();
		config = new Config();
	}

	/**
	 * @return Insane instance
	 */
	public static final Insane getInstance() {
		return insane;
	}

	/**
	 * @return the name of the client
	 */
	public final String getClientName() {
		return clientName;
	}

	/**
	 * @return the version of the client
	 */
	public final String getClientVersion() {
		return clientVersion;
	}

	/**
	 * @return the mc interface
	 */
	public final IMinecraftClient getIMinecraftClient() {
		return imc;
	}

	/**
	 * @return the config
	 */
	public final Config getConfig() {
		return config;
	}

	/**
	 * @return the logger
	 */
	public final Logger getLogger() {
		return logger;
	}

	/**
	 * @return the module manager
	 */
	public final ModuleManager getModuleManager() {
		return moduleManager;
	}

	/**
	 * @return the command manager
	 */
	public final CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * Called when mc gets closed.
	 */
	public final void shutdown() {
		moduleManager.shutdown();
		config.save();
	}

	/**
	 * @return true if the config should be loaded.
	 */
	public final boolean shouldLoadConfig() {
		return loadConfig;
	}

	/**
	 * Sets loadConfig false
	 */
	public final void dontLoadConfigAgain() {
		this.loadConfig = false;
	}
}