package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class DoubleValue extends Value {

	private final double min, max, increment;
	private double value;

	public DoubleValue(final String name, final boolean visibleInArrayList, final double value, final double min, final double max, final double increment) {
		super(name, visibleInArrayList);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	public final double getValue() {
		return value;
	}

	public final void setValue(final double value) {
		this.value = Math.round(MathHelper.clamp(value, min, max) / increment) * increment;
	}

	public final double getMin() {
		return min;
	}

	public final double getMax() {
		return max;
	}

	public final double getIncrement() {
		return increment;
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}