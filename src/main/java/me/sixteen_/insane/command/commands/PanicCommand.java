package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class PanicCommand extends Command {

	public PanicCommand() {
		super("panic");
	}

	@Override
	public final String syntax() {
		return String.format("%s", getName());
	}

	@Override
	public final void run(final String... param) {
		for (final Module m : insane.getModuleManager().getModules()) {
			if (m.isEnabled()) {
				m.disable();
			}
		}
	}
}