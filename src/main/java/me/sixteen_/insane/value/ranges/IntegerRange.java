package me.sixteen_.insane.value.ranges;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class IntegerRange extends Value {

	private final int min, max;
	private int minValue, maxValue;

	public IntegerRange(String name, boolean visibleInArrayList, final int minValue, final int maxValue, final int min, final int max) {
		super(name, visibleInArrayList);
		this.minValue = minValue < maxValue ? minValue : maxValue;
		this.maxValue = minValue < maxValue ? maxValue : minValue;
		this.min = min;
		this.max = max;
	}

	public final int getMinValue() {
		return minValue;
	}

	public final void setMinValue(final int minValue) {
		this.minValue = MathHelper.clamp(minValue, min, maxValue);
	}

	public final int getMaxValue() {
		return maxValue;
	}

	public final void setMaxValue(final int maxValue) {
		this.maxValue = MathHelper.clamp(maxValue, minValue, max);
	}

	public final int getMin() {
		return min;
	}

	public final int getMax() {
		return max;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", getMinValue(), getMaxValue());
	}
}