package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class IntegerValue extends Value {

	private final int min, max;
	private int value;

	public IntegerValue(final String name, final boolean visibleInArrayList, final int value, final int min, final int max) {
		super(name, visibleInArrayList);
		this.value = value;
		this.min = min;
		this.max = max;
	}

	public final int getValue() {
		return value;
	}

	public final void setValue(final int value) {
		this.value = value;
	}

	public final int getMin() {
		return min;
	}

	public final int getMax() {
		return max;
	}

	@Override
	public final String toString() {
		return String.valueOf(getValue());
	}
}