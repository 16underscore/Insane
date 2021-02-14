package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ToggleCommand extends Command {

	public ToggleCommand() {
		super("toggle");
	}

	@Override
	public void runCommand(final String... param) {
		insane.getModuleManager().getModuleByName(param[1]).toggle();
	}

	@Override
	public String syntax() {
		return String.format(".%s <Module>", getName());
	}
}