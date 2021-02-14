package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
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
		InputUtil.Key key;
		if (param[2].equalsIgnoreCase("none")) {
			key = InputUtil.UNKNOWN_KEY;
		} else {
			key = InputUtil.fromTranslationKey(String.format("key.keyboard.%s", param[2].toLowerCase()));
		}
		insane.getModuleManager().getModuleByName(param[1]).setKeybind(key);
	}

	@Override
	public String syntax() {
		return String.format(".%s <Module> <Key>", getName());
	}
}