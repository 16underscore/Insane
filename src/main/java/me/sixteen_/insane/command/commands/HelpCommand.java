package me.sixteen_.insane.command.commands;

import java.util.ArrayList;
import java.util.List;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.utils.Logger;

/**
 * @author 16_
 */
public class HelpCommand extends Command {
	
	private Insane insane;

	public HelpCommand() {
		super("help", "h");
	}
	
	@Override
	public void runCommand(final String... param) {
		final List<String> message = new ArrayList<String>();
		try {
			insane = Insane.getInsane();
			if (param[1].equalsIgnoreCase("command")) {
				message.add("Commands:");
				for(final Command c : insane.getCommandManager().getCommands()) {
					message.add(c.getDefaultName());
				}
			} else if (param[1].equalsIgnoreCase("module")) {
				message.add("Modules:");
				for(final Module m : insane.getModuleManager().getModules()) {
					message.add(m.getName());
				}
			} else {
				message.add(String.format("%s is an invalid parameter", param[1]));
				throw new Exception();
			}
		} catch (Exception e) {
			message.add(".help command | .help module");
		}
		for (String s : message) {
			Logger.getLogger().addChatMessage(s);
		}
	}
}
