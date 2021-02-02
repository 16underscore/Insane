package me.sixteen_.insane.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 16_
 */
public abstract class Command {

	private final List<String> names = new ArrayList<String>();

	public Command(final String name, final String... names) {
		this.names.add(name);
		Collections.addAll(this.names, names);
	}

	public abstract void runCommand(final String... param);

	public String getDefaultName() {
		return names.get(0);
	}

	public List<String> getNames() {
		return names;
	}
}