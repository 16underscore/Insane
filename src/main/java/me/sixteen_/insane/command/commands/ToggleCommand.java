package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleManager;

/**
 * @author 16_
 */
public class ToggleCommand extends Command {

	private ModuleManager mm;

	public ToggleCommand() {
		super("t");
	}

	@Override
	public void runCommand(final String... param) {
		mm = Insane.getInsane().getModuleManager();
		Module m = mm.getModuleByName(param[1]);
		m.toggle();
	}
}
