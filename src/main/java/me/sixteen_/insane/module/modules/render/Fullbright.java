package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Fullbright extends Module {

	private double previousGamma;

	public Fullbright() {
		super("Fullbright");
	}

	@Override
	protected final void onEnable() {
		previousGamma = mc.options.gamma;
		mc.options.gamma = 69.0D;
	}

	@Override
	protected final void onDisable() {
		mc.options.gamma = previousGamma;
	}

	@Override
	protected final void onShutdown() {
		mc.options.gamma = previousGamma;
	}
}