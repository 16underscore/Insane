package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class Velocity extends Module {

	public Velocity() {
		super(Velocity.class.getSimpleName());
	}

	@Override
	protected void onUpdate() {
	}
}