package me.sixteen_.insane.command;

import java.util.List;

import me.sixteen_.insane.command.commands.BindCommand;
import me.sixteen_.insane.command.commands.LoginCommand;
import me.sixteen_.insane.command.commands.ModeCommand;
import me.sixteen_.insane.command.commands.ToggleCommand;
import me.sixteen_.insane.util.Logger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class CommandManager {

	private final List<Command> commands = new java.util.ArrayList<Command>();

	public CommandManager() {
		addCommand(new ToggleCommand());
		addCommand(new LoginCommand());
		addCommand(new BindCommand());
		addCommand(new ModeCommand());
	}

	private void addCommand(final Command cmd) {
		commands.add(cmd);
	}

	public void commandInput(final String input) {
		final String[] cmd = input.substring(1).split(" ");
		for (final Command command : commands) {
			if (command.getName().equalsIgnoreCase(cmd[0])) {
				try {
					command.runCommand(cmd);
				} catch (Exception e) {
					Logger.getLogger().addChatMessage(command.commandSyntax());
				}
				return;
			}
		}
	}

	public List<Command> getCommands() {
		return commands;
	}
}