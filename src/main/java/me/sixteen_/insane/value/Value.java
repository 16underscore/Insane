package me.sixteen_.insane.value;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Value {

	private final String name;
	private final boolean visibleInArrayList;

	public Value(final String name, final boolean visibleInArrayList) {
		this.name = name;
		this.visibleInArrayList = visibleInArrayList;
	}

	public final String getName() {
		return name;
	}

	public final boolean isVisibleInArrayList() {
		return visibleInArrayList;
	}

	@Override
	public abstract String toString();
}