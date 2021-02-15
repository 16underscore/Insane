package me.sixteen_.insane.value.values;

import java.util.Arrays;
import java.util.List;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ListValue extends Value {

	private final List<String> values;
	private int index;

	public ListValue(final String name, final String... values) {
		this.name = name;
		this.values = Arrays.asList(values);
		index = 0;
	}

	public final String getValue() {
		return values.get(index);
	}

	public final void setValue(final String value) {
		index = values.indexOf(value);
	}

	public final boolean is(final String value) {
		return index == values.indexOf(value);
	}
}
