package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;
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
	public final void run(final String... param) {
		for (final Module m : insane.getModuleManager().getModules()) {
			insane.getLogger().log(m.getName());
		}
	}

	@Override
	public final String syntax() {
		return String.format("%s", getName());
	}
}