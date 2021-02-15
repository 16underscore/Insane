package me.sixteen_.insane.value.values;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class FloatValue extends NumberValue {

	private float value, minimum, maximum, increment;

	public FloatValue(final String name, final float value, final float minimum, final float maximum, final float increment) {
		this.name = name;
		this.value = value;
		this.minimum = minimum;
		this.maximum = maximum;
		this.increment = increment;
	}

	@Override
	public final void increment(final boolean positive) {
		setValue(getValue() + (positive ? 1 : -1) * increment);
	}

	public final float getValue() {
		return value;
	}

	public final void setValue(final float value) {
		this.value = Math.round(MathHelper.clamp(value, minimum, maximum) / increment) * increment;
	}

	public final float getMinimum() {
		return minimum;
	}

	public final void setMinimum(final float minimum) {
		this.minimum = minimum;
	}

	public final float getMaximum() {
		return maximum;
	}

	public final void setMaximum(final float maximum) {
		this.maximum = maximum;
	}

	public final float getIncrement() {
		return increment;
	}

	public final void setIncrement(final float increment) {
		this.increment = increment;
	}
}