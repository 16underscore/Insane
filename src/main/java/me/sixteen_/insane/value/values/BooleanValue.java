package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class BooleanValue extends Value {

	private boolean value;

	public BooleanValue(final String name, final boolean visibleInArrayList, final boolean value) {
		super(name, visibleInArrayList);
		this.value = value;
	}

	@Override
	public final String toString() {
		return String.valueOf(getValue());
	}

	public final boolean getValue() {
		return value;
	}

	public final void setValue(final boolean value) {
		this.value = value;
	}
}