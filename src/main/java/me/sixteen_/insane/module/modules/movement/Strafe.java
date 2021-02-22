package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class Strafe extends Module {

	public Strafe() {
		super("Strafe");
	}

	@Override
	public void onUpdate() {
		if (mc.player.getSpeed() == 0F) {
			return;
		}
		mc.player.abilities.setWalkSpeed(2F);
	}
}
