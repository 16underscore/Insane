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
	private float previousSpeed;
	private boolean flyingbefore;

	public Fly() {
		super(Fly.class.getSimpleName());
		speed = new FloatValue("speed", true, 1, 0, 2, 0.1F);
		addValues(speed);
	}

	@Override
	public void onUpdateValue() {
		mc.player.abilities.setFlySpeed(speed.getValue() / 10);
	}

	@Override
	protected final void onEnable() {
		flyingbefore = mc.player.abilities.flying;
		if (!flyingbefore) {
			mc.player.addVelocity(0D, 0.1D, 0D);
			mc.player.abilities.flying = true;
		}
		previousSpeed = mc.player.abilities.getFlySpeed();
		onUpdateValue();
	}

	@Override
	protected final void onDisable() {
		mc.player.abilities.setFlySpeed(previousSpeed);
		mc.player.abilities.flying = flyingbefore;
	}
}