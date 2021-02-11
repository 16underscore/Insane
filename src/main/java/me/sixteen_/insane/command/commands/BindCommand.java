package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.InputUtil;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class BindCommand extends Command {

	public BindCommand() {
		super("bind");
	}

	@Override
	public void runCommand(final String... param) {
		final Module m = Insane.getInsane().getModuleManager().getModuleByName(param[1]);
		InputUtil.Key key;
		if (param[2].equalsIgnoreCase("none")) {
			key = InputUtil.UNKNOWN_KEY;
		} else {
			key = InputUtil.fromTranslationKey(String.format("key.keyboard.%s", param[2].toLowerCase()));
		}
		m.setKeybind(key);
	}

	@Override
	public String commandSyntax() {
		return String.format(".%s <Module> <Key>", getName());
	}
}