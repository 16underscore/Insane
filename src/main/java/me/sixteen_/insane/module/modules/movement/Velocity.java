package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Velocity extends Module {

	//TODO velocity.
	public Velocity() {
		super(Velocity.class.getSimpleName(), ModuleCategory.MOVEMENT);
	}

	@Override
	protected final void onUpdate() {
	}
}