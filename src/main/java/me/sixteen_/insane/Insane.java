package me.sixteen_.insane;

import me.sixteen_.insane.command.CommandManager;
import me.sixteen_.insane.module.ModuleManager;
import me.sixteen_.insane.ntrfc.IMinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class Insane implements ClientModInitializer {

	// Instance
	private static Insane insane;
	
	// Client information
	private final String clientName;
	private final String clientVersion;
	
	// Managers
	private final ModuleManager moduleManager;
	private final CommandManager commandManager;
	
	private final IMinecraftClient imc = (IMinecraftClient) MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		insane = new Insane();
	}

	public Insane() {
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();
		clientName = "Insane";
		clientVersion = "b1";
	}

	public String getClientName() {
		return clientName;
	}

	public String getClientVersion() {
		return clientVersion;
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