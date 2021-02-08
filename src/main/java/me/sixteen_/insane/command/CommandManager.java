package me.sixteen_.insane.command;

import java.util.List;

import me.sixteen_.insane.command.commands.BindCommand;
import me.sixteen_.insane.command.commands.LoginCommand;
import me.sixteen_.insane.command.commands.ModeCommand;
import me.sixteen_.insane.command.commands.ToggleCommand;
import me.sixteen_.insane.utils.Logger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class CommandManager {

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
		String[] cmd = input.substring(1).split(" ");
		runCommand(cmd);
	}

	public List<Command> getCommands() {
		return commands;
	}

	private void runCommand(final String... cmd) {
		for (Command command : commands) {
			for (String name : command.getNames()) {
				if (name.equalsIgnoreCase(cmd[0])) {
					try {
						command.runCommand(cmd);
					} catch (Exception e) {
						Logger.getLogger().addChatMessage(command.commandSyntax());
					}
					return;
				}
			}
		}
	}
}