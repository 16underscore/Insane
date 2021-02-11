package me.sixteen_.insane.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Command {

	private final String name;

	public Command(final String name) {
		this.name = name;
	}

	public abstract void runCommand(final String... param);
	public abstract String commandSyntax();

	public String getName() {
		return name;
	}
}