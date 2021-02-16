package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class NumberValue extends Value {

	public NumberValue(final String name, final boolean visibleInArrayList) {
		super(name, visibleInArrayList);
	}

	protected abstract void increment(final boolean positive);
}
