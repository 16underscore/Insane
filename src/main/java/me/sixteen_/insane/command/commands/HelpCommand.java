package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.utils.Logger;

/**
 * @author 16_
 */
public class HelpCommand extends Command {

	private final Logger logger;

	public HelpCommand() {
		super("help", "h");
		logger = Logger.getLogger();
	}

	@Override
	public void runCommand(final String... param) {
		logger.addChatMessage("Commands:");
		for (final Command c : Insane.getInsane().getCommandManager().getCommands()) {
			logger.addChatMessage(c.getDefaultName());
		}
	}

	@Override
	public String commandSyntax() {
		return ".h";
	}
}
