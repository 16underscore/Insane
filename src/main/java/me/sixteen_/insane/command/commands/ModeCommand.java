package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ModeCommand extends Command {

	public ModeCommand() {
		super("mode");
	}

	@Override
	public void run(final String... param) {
		insane.getModuleManager().getModuleByName(param[1]).setMode(param[2]);
	}

	@Override
	public String syntax() {
		return String.format(".%s <Module> <Mode>", getName());
	}
}