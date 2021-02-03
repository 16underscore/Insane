package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;

/**
 * @author 16_
 */
public class ToggleCommand extends Command {

	public ToggleCommand() {
		super("toggle", "t");
	}

	@Override
	public void runCommand(final String... param) {
		final Module m = Insane.getInsane().getModuleManager().getModuleByName(param[1]);
		m.toggle();
	}

	@Override
	public String commandSyntax() {
		return String.format(".%s <Module>", getDefaultName());
	}
}