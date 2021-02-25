package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ConfigCommand extends Command {

	public ConfigCommand() {
		super("config");
	}

	@Override
	public final void run(final String... param) {
		if (param[1].equals("load")) {
			insane.getConfig().load();
		} else if (param[1].equals("save")) {
			insane.getConfig().save();
		}
	}

	@Override
	public final String syntax() {
		return String.format(".%s <load|save>", getName());
	}
}
