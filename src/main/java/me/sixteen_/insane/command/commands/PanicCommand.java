package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
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
	public void run(final String... param) {
		insane.getModuleManager().getModules().forEach(m -> {
			if (m.isEnabled())
				m.disable();
		});
	}

	@Override
	public String syntax() {
		return String.format("%s", getName());
	}
}