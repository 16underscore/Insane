package me.sixteen_.insane.value.values;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class DoubleValue extends NumberValue {

	private double value, minimum, maximum, increment;

	public DoubleValue(final String name, final double value, final double minimum, final double maximum, final double increment) {
		this.name = name;
		this.value = value;
		this.minimum = minimum;
		this.maximum = maximum;
		this.increment = increment;
	}

	public void increment(final boolean positive) {
		setValue(getValue() + (positive ? 1 : -1) * increment);
	}

	public final double getValue() {
		return value;
	}

	public final void setValue(final double value) {
		this.value = Math.round(MathHelper.clamp(value, minimum, maximum) / increment) * increment;
	}

	public final double getMinimum() {
		return minimum;
	}

	public final void setMinimum(final double minimum) {
		this.minimum = minimum;
	}

	public final double getMaximum() {
		return maximum;
	}

	public final void setMaximum(final double maximum) {
		this.maximum = maximum;
	}

	public final double getIncrement() {
		return increment;
	}

	public final void setIncrement(final double increment) {
		this.increment = increment;
	}
}