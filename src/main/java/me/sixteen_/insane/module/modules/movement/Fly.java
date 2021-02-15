package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Fly extends Module {

	public Fly() {
		super("Fly", ModuleCategory.MOVEMENT);
	}

	@Override
	protected final void onEnable() {
		mc.player.addVelocity(0D, 0.1D, 0D);
		mc.player.abilities.flying = true;
	}

	@Override
	protected final void onDisable() {
		mc.player.abilities.flying = false;
	}
}