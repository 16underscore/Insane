package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class DamageJump extends Module {

	public DamageJump() {
		super(DamageJump.class.getSimpleName());
	}

	@Override
	protected void onUpdate() {
	}
}