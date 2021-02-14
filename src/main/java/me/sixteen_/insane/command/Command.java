package me.sixteen_.insane.command;

import me.sixteen_.insane.Insane;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Command {

	private final String name;
	protected final MinecraftClient mc;
	protected final Insane insane;

	public Command(final String name) {
		this.name = name;
		mc = MinecraftClient.getInstance();
		insane = Insane.getInstance();
	}

	public abstract void run(final String... param);
	public abstract String syntax();

	public String getName() {
		return name;
	}
}