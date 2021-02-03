package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;

/**
 * @author 16_
 */
public class ModeCommand extends Command {

	public ModeCommand() {
		super("mode", "m");
	}

	@Override
	public void runCommand(final String... param) {
		final Module m = Insane.getInsane().getModuleManager().getModuleByName(param[1]);
		m.setMode(param[2]);
	}

	@Override
	public String commandSyntax() {
		return String.format(".%s <Module> <Mode>", getDefaultName());
	}
}
