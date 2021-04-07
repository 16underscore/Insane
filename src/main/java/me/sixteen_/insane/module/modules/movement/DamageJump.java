package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class DamageJump extends Module {

	// TODO Jump as high as possible, when player gets Hurt.
	public DamageJump() {
		super(DamageJump.class.getSimpleName(), ModuleCategory.MOVEMENT);
	}

	@Override
	protected void onUpdate() {
	}
}