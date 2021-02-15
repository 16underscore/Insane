package me.sixteen_.insane.value;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Value {

	protected String name;

	public final String getName() {
		return name;
	}
}