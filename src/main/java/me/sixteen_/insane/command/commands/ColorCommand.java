package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ColorCommand extends Command {

	public ColorCommand() {
		super("color");
	}

	@Override
	public final String syntax() {
		return String.format(".%s <color>", getName());
	}

	@Override
	public final void run(final String... param) {
		Insane.getInstance().setClientColor(param[1]);
	}
}