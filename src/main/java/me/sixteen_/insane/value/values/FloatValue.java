package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class FloatValue extends Value {

	private final float min, max, increment;
	private float value;

	public FloatValue(final String name, final boolean visibleInArrayList, final float value, final float min, final float max, final float increment) {
		super(name, visibleInArrayList);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	public final float getValue() {
		return value;
	}

	public final void setValue(final float value) {
		this.value = Math.round(MathHelper.clamp(value, min, max) / increment) * increment;
	}

	public final float getMin() {
		return min;
	}

	public final float getMax() {
		return max;
	}

	public final float getIncrement() {
		return increment;
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}