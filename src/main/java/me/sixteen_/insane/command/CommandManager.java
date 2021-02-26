package me.sixteen_.insane.command;

import java.util.List;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.commands.BindCommand;
import me.sixteen_.insane.command.commands.ConfigCommand;
import me.sixteen_.insane.command.commands.HelpCommand;
import me.sixteen_.insane.command.commands.LoginCommand;
import me.sixteen_.insane.command.commands.PanicCommand;
import me.sixteen_.insane.command.commands.ToggleCommand;
import me.sixteen_.insane.command.commands.ValueCommand;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class CommandManager {

	private final List<Command> commands = new java.util.ArrayList<Command>();
	private final String prefix = ".";
	private final Insane insane;

	public CommandManager() {
		this.insane = Insane.getInstance();
		addCommand(new ConfigCommand());
		addCommand(new ToggleCommand());
		addCommand(new LoginCommand());
		addCommand(new PanicCommand());
		addCommand(new ValueCommand());
		addCommand(new BindCommand());
		addCommand(new HelpCommand());
	}

	private final void addCommand(final Command cmd) {
		commands.add(cmd);
	}

	public final void input(final String input) {
		final String[] cmd = input.substring(prefix.length()).split(" ");
		for (final Command command : commands) {
			if (command.getName().equalsIgnoreCase(cmd[0])) {
				try {
					command.run(cmd);
				} catch (Exception e) {
					insane.getLogger().log(command.syntax());
				}
				return;
			}
		}
	}

	public final List<Command> getCommands() {
		return commands;
	}
}