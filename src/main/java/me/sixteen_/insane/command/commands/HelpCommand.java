package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class HelpCommand extends Command {

	public HelpCommand() {
		super("help");
	}

	@Override
	public void runCommand(String... param) {
	}

	@Override
	public String syntax() {
		return String.format("%s", getName());
	}
}