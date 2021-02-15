package me.sixteen_.insane.value.values;

import me.sixteen_.insane.value.Value;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class BooleanValue extends Value {

	private boolean enabled;

	public BooleanValue(final String name, final boolean enabled) {
		this.name = name;
		this.enabled = enabled;
	}

	public final void toggle() {
		enabled = !enabled;
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}
}