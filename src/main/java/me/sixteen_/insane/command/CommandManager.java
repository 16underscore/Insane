package me.sixteen_.insane.command;

import java.util.List;

import me.sixteen_.insane.command.commands.BindCommand;
import me.sixteen_.insane.command.commands.HelpCommand;
import me.sixteen_.insane.command.commands.ModeCommand;
import me.sixteen_.insane.command.commands.ToggleCommand;
import me.sixteen_.insane.utils.Logger;

/**
 * @author 16_
 */
public class CommandManager {

	private final List<Command> commands = new java.util.ArrayList<Command>();
	private Logger logger;

	public CommandManager() {
		addCommand(new ToggleCommand());
		addCommand(new BindCommand());
		addCommand(new HelpCommand());
		addCommand(new ModeCommand());
		logger = Logger.getLogger();
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
						logger.addChatMessage(command.commandSyntax());
					}
					return;
				}
			}
		}
	}
}