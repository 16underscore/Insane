package me.sixteen_.insane;

import java.awt.Color;

import me.sixteen_.insane.command.CommandManager;
import me.sixteen_.insane.module.ModuleManager;
import me.sixteen_.insane.ntrfc.IMinecraftClient;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

/**
 * @author 16_
 */
public class Insane implements ModInitializer {

	// Instance
	private static Insane insane;
	
	// Client information
	private final String clientName;
	private final String clientVersion;
	
	// Client colors
	private final Color clientColor;
	private final Color clientAccentColor;
	
	// Managers
	private final ModuleManager moduleManager;
	private final CommandManager commandManager;
	
	private final IMinecraftClient imc = (IMinecraftClient) MinecraftClient.getInstance();

	public Insane() {
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();
		clientColor = new Color(20, 20, 20, 191);
		clientAccentColor = new Color(255, 85, 255, 191);
		clientName = "Insane";
		clientVersion = "b1";
	}

	@Override
	public void onInitialize() {
		insane = new Insane();
	}

	public String getClientName() {
		return clientName;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public Color getClientColor() {
		return clientColor;
	}

	public Color getClientAccentColor() {
		return clientAccentColor;
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public static Insane getInsane() {
		return insane;
	}

	public void shutdown() {
		moduleManager.shutdown();
	}

	public IMinecraftClient getIMinecraftClient() {
		return imc;
	}
}