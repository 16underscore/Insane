package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.value.values.FloatValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Fly extends Module {

	private final FloatValue speed;

	public Fly() {
		super("Fly");
		speed = new FloatValue("speed", true, 1, 0, 2, 0.1F);
		addValues(speed);
	}

	@Override
	protected final void onEnable() {
		mc.player.addVelocity(0D, 0.1D, 0D);
		mc.player.abilities.flying = true;
		onUpdateValue();
	}

	@Override
	protected final void onDisable() {
		mc.player.abilities.flying = false;
	}

	@Override
	public void onUpdateValue() {
		mc.player.abilities.setFlySpeed(speed.getValue() / 10);
	}
}