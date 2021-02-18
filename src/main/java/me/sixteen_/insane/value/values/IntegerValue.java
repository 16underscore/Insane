package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class IntegerValue extends Value {

	private int value, minimum, maximum;

	public IntegerValue(final String name, final boolean visibleInArrayList, final int value, final int minimum, final int maximum) {
		super(name, visibleInArrayList);
		this.value = value;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public final int getValue() {
		return value;
	}

	public final void setValue(final int value) {
		this.value = value;
	}

	public final int getMinimum() {
		return minimum;
	}

	public final void setMinimum(final int minimum) {
		this.minimum = minimum;
	}

	public final int getMaximum() {
		return maximum;
	}

	public final void setMaximum(final int maximum) {
		this.maximum = maximum;
	}

	@Override
	public String toString() {
		return null;
	}
}