package me.sixteen_.insane.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Command {

	private final List<String> names = new ArrayList<String>();

	public Command(final String name, final String... names) {
		this.names.add(name);
		Collections.addAll(this.names, names);
	}

	public abstract void runCommand(final String... param);
	public abstract String commandSyntax();

	public String getDefaultName() {
		return names.get(0);
	}

	public List<String> getNames() {
		return names;
	}
}