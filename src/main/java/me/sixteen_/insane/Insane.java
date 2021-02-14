package me.sixteen_.insane;

import me.sixteen_.insane.command.CommandManager;
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
	private final String clientName;
	private final String clientVersion;
	// Managers
	private final ModuleManager moduleManager;
	private final CommandManager commandManager;
	// Logger
	private final Logger logger;
	// Minecraft
	private final IMinecraftClient imc;

	@Override
	public void onInitializeClient() {
	}

	public Insane() {
		insane = this;
		clientName = "Insane";
		clientVersion = "b2";
		imc = (IMinecraftClient) MinecraftClient.getInstance();
		logger = new Logger();
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();
	}

	public static Insane getInstance() {
		return insane;
	}

	public String getClientName() {
		return clientName;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public IMinecraftClient getIMinecraftClient() {
		return imc;
	}

	public Logger getLogger() {
		return logger;
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public void shutdown() {
		moduleManager.shutdown();
	}
}